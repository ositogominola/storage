from flask import Blueprint,request
from controller.ventaController import ventaController
from controller.superController import superController
from controller.invoiceController import invoiceController

venta=Blueprint('venta', __name__)
vnt=ventaController()
created=superController()
inv=invoiceController
@venta.route("/venta", methods=['POST'])
def create():
    data = request.get_json()
    return created.POST(data)

@venta.route("/venta_product_id/<int:id>",methods=['GET'])
def get_all_by_id(id):
    return vnt.GET_BY_ID(id)

@venta.route("/venta_facturacion_id/<empresa>",methods=['GET'])
def get_all(empresa):
    da=invoiceController()
    return da.get_all_by_id(empresa)