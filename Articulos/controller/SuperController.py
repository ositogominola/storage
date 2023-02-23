from .ControllerProd import ControlerProduct
from .ControllerInfProduc import InfProdController
from flask import jsonify

class SuperController:

    ProductoContro=ControlerProduct()
    infoProduc=InfProdController()

    def post_all(self, data):
        dicti = {}

        # Verificar si el stock es mayor a cero
        if data["infoPro"]["stock"] <= 0:
            return jsonify({"error": "El stock debe ser mayor a cero"})

        # Crear el producto
        Prd = self.ProductoContro.post(data["Product"], data["infoPro"]["idEmpresa"])

        if Prd["successful"] is False:
            return jsonify({"error": "No se pudo crear el producto error: producto"})
        else:
            dicti["producto"] = Prd

            # Actualizar el idProducto en infoPro
            data["infoPro"]["idProducto"] = Prd["Producto"]

            # Crear la información del producto
            inf = self.infoProduc.post(data["infoPro"])

            if inf["successful"]:
                dicti["infoProducto"] = inf
            else:
                # Si no se pudo crear la información, eliminar el producto creado anteriormente
                self.ProductoContro.delete(Prd["Producto"])
                return jsonify({"error": "No se pudo crear el producto error: infoproducto"})

        return jsonify(dicti)
