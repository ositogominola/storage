from flask import Blueprint,request
from controller.ventaController import ventaController
from controller.superController import superController
from controller.invoiceController import invoiceController

venta=Blueprint('venta', __name__)
vnt=ventaController()
superC=superController()
inv=invoiceController()
@venta.route("/venta", methods=['POST'])
def create():
    data = request.get_json()
    return superC.POST(data)

@venta.route("/venta_product_id/<int:id>",methods=['GET'])
def get_all_by_id_product(id):
    return vnt.GET_BY_ID(id)

@venta.route("/facturas",methods=['GET'])
def get_all():
    return inv.get_all()

@venta.route("/venta_facturacion_id/<empresa>",methods=['GET'])
def get_all_by_empresa(empresa):
    return inv.get_all_by_id_empr(empresa)

@venta.route("/delete_facturacion_id/<int:id>",methods=['DELETE'])
def delete_fact_empresa(id):
    return inv.delete_by_id(id)