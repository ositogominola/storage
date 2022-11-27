from flask import Blueprint,jsonify,request
from controller.ControllerProd import ControlerProduct
from controller.ControllerInfProduc import InfProdController
from controller.SuperController import SuperController

Product=Blueprint('Product', __name__)
controllerPro=ControlerProduct()
superCont=SuperController()
inf=InfProdController()
@Product.route("/producto", methods=["POST"])
def create():
    data=request.get_json()
    return superCont.post_all(data)

@Product.route("/producto/id/<idEmpresa>", methods=["GET"])
def get_Company(idEmpresa):
    return controllerPro.get_by_company(idEmpresa)

@Product.route("/producto", methods=["GET"])
def get_all():
    return controllerPro.get_all()

@Product.route("/producto/<int:id>", methods=["GET"])
def get_by_id(id):
    return controllerPro.get_by_id(id)

@Product.route("/producto/<int:id>", methods=["DELETE"])
def delete(id):
    return controllerPro.delete(id)

@Product.route("/producto/filter", methods=["GET"])
def get_by_data():
    req=request.get_json()
    return controllerPro.get_by_data(**req)

@Product.route("/producto/update/<int:id>", methods=["PATCH"])
def update(id):
    req=request.get_json()
    return controllerPro.update(id,**req)