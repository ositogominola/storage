from .ControllerProd import ControlerProduct
from .ControllerInfProduc import InfProdController
from flask import jsonify

class SuperController:

    ProductoContro=ControlerProduct()
    infoProduc=InfProdController()

    def post_all(self, data):
        dicti = {}
        try:
            # Verificar si el stock es mayor a cero
            if data["infoPro"]["stock"] <= 0 or data["infoPro"]["costoCompra"] <= 0 or data["infoPro"]["costoVenta"] <= 0:
                productoU = None
                successful = False
                message = "error: no se permiten numeros negativos"
            else:
                # Crear el producto
                Prd = self.ProductoContro.post(data["Product"], data["infoPro"]["idEmpresa"])
                if Prd["successful"] is False:
                    productoU = None
                    successful = False
                    message = Prd["message"]
                else:
                    # Actualizar el idProducto en infoPro
                    data["infoPro"]["idProducto"] = Prd["Producto"].id

                    # Crear la información del producto
                    inf = self.infoProduc.post(data["infoPro"])
                    if inf["successful"]:
                        productoU = {"productoID":Prd["Producto"].id, "infProdID":inf["IDprodInf"].id}
                        successful = True
                        message = "producto creado"
                    else:
                        productoU = None
                        successful = False
                        message = inf["message"]
                        self.ProductoContro.delete(Prd["Producto"])
        except Exception as e:
            productoU = None
            successful = False
            message = "ERROR: {}, TYPE: {}".format(e.args, type(e))
        dicti["Producto"] = productoU
        dicti["successful"] = successful
        dicti["message"] = message
        return jsonify(dicti)
