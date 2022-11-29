from flask import Blueprint,request
from controller.ventaController import ventaController
from controller.superController import superController

venta=Blueprint('venta', __name__)
vnt=ventaController()
created=superController()

@venta.route("/venta", methods=['POST'])
def create():
    data = request.get_json()
    return created.POST(data)

@venta.route("/venta",methods=['GET'])
def get_all():
    return vnt.GET_ALL()