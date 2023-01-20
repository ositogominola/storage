from .ControllerProd import ControlerProduct
from .ControllerInfProduc import InfProdController
from flask import jsonify

class SuperController:

    ProductoContro=ControlerProduct()
    infoProduc=InfProdController()

    def post_all(self, data):
        dicti={}
        if data["infoPro"]["stock"]>0:
            Prd=self.ProductoContro.post(data["Product"],data["infoPro"]["idEmpresa"])
        else:
            Prd=None
        dicti["producto"]=Prd
        if Prd is not None:
            print("si es")
            data["infoPro"]["idProducto"]=Prd["Producto"]
            inf=self.infoProduc.post(data["infoPro"])
            dicti["infoProducto"]=inf
        return jsonify(dicti)