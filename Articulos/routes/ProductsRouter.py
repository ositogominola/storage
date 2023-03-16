from flask import Blueprint, jsonify, request
from controller.ControllerProd import ControlerProduct
from controller.ControllerInfProduc import InfProdController
from controller.SuperController import SuperController
from models.Product import Producto as Pr
from schemas.Productschema import ProductSchema

Product = Blueprint('Product', __name__)
controllerPro = ControlerProduct()
superCont = SuperController()
inf = InfProdController()


# crea el producto con su informacion
@Product.route("/producto", methods=["POST"])
def create():
    data = request.get_json()
    return superCont.post_all(data)


# obtiene el producto por el id de la empresa
@Product.route("/producto/id/<idEmpresa>", methods=["GET"])
def get_Company(idEmpresa):
    return controllerPro.get_by_company(idEmpresa)


# obtiene todos los productos     !!!! un usuario normal no tiene acceso a esta funcion
@Product.route("/producto", methods=["GET"])
def get_all():
    return controllerPro.get_all()


# obtiene los productos por el id del producto
@Product.route("/producto/<id>", methods=["GET"])
def get_by_id(id):
    idEmpresa=request.get_json()
    return controllerPro.get_by_id(id,idEmpresa)

# elimina un producto por el id
@Product.route("/producto/<id>", methods=["DELETE"])
def delete(id):
    idEmpresa=request.get_json()
    return controllerPro.delete(id,idEmpresa)


# filtra los productos por medio de un json pasado por el request
@Product.route("/producto/filter/<empresa>", methods=["GET"])
def get_by_data(empresa):
    req = request.get_json()
    return controllerPro.get_by_data(empresa, **req)


# actualiza el producto con su informacion
@Product.route("/producto/update/<id>", methods=["PATCH"])
def update(id):
    req = request.get_json()
    return controllerPro.update(id, **req)
