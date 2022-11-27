from .ControllerProd import ControlerProduct
from .ControllerInfProduc import InfProdController
from flask import jsonify

class SuperController:

    ProductoContro=ControlerProduct()
    infoProduc=InfProdController()

    def post_all(self, data):
        dicti={}
        Prd=self.ProductoContro.post(data["Product"],data["infoPro"]["idEmpresa"],data["infoPro"])
        dicti["producto"]=Prd

        if Prd["Producto"] is not None:
            data["infoPro"]["idProducto"]=Prd["Producto"]
            inf=self.infoProduc.post(data["infoPro"])
            dicti["infoProducto"]=inf
        return jsonify(dicti)