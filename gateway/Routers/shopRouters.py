from flask import Blueprint, request
import requests
from settings.loadFileConfig import loadFileConfig

ventas = Blueprint("ventas", __name__)
dataConfig = loadFileConfig()



# crear venta con facturas (se puede comprar varios articulos a la vez)
@ventas.route("/create_vent", methods=['POST'])
def create_vent():
    cant = False
    json = {}

    # comprobar si existe el producto
    headers = {"Content-Type": "application/json; charset=utf-8"}
    data = request.get_json()
    url = "http://" + dataConfig["url-backend"] + ":" + str(dataConfig["port"])
    dontSave = []
    for prd in data["prd"]:
        if data["empresa"] == prd["idempresa"]:
            urlp = url + "/articulo_id/" + str(prd["idproducto"])
            json = {"idEmpresa": prd["idempresa"]}
            response = requests.get(urlp, headers=headers, json=json)
            producto = response.json()["Producto"]
            if producto is None:
                dontSave.append(prd)
            else:
                if int(producto["prdinfo"]["stock"]) <= prd["cantidad"]:
                    dontSave.append(prd)
                prd["precioUn"] = producto["prdinfo"]["costoVenta"]
        else:
            dontSave.append(prd)

    for dat in dontSave:
        data["prd"].remove(dat)

    if data["prd"]:
        url = dataConfig["url-backend-shopp"] + "/venta"
        response = requests.post(url, headers=headers, json=data)
        datos=response.json()
        successful=True
        message="datos creados exitosamente"
    else:
        datos = None
        successful = False
        message = "no se pudo registrar los datos"

    json["datos"]=datos
    json["successful"]=successful
    json["message"]=message
    return json


#        obtienen uno solo

# obtiene un producto vendido por id
@ventas.route("/vent_prod_id/<id>", methods=['GET'])
def get_vent_by_id(id):
    headers = {"Content-Type": "application/json; charset=utf-8"}
    url = dataConfig["url-backend-shopp"] + "/venta_product_id/" + id
    response = requests.get(url, headers=headers)
    return response.json()


# obtiene una venta en facturacion de la empresa por su id
@ventas.route("/vent_fact_id/<id>", methods=['GET'])
def get_fact_pro_by_id_emp(id):
    headers = {"Content-Type": "application/json; charset=utf-8"}
    url = dataConfig["url-backend-shopp"] + "/get_only_fact_id/" + id
    response = requests.get(url, headers=headers)
    return response.json()


#               obtiene todos

# obtiene todos los productos vendidos por empresa
@ventas.route("/vent_prod_id_empr/<id>", methods=['GET'])
def get_vent_pro_by_id_emp(id):
    headers = {"Content-Type": "application/json; charset=utf-8"}
    url = dataConfig["url-backend-shopp"] + "/get_prod_id_em/" + id
    response = requests.get(url, headers=headers)
    return response.json()


# obtiene todas las facturas por id de empresas
@ventas.route("/vent_id_empresa/<empresa>", methods=['GET'])
def get_vent_by_id_empr(empresa):
    headers = {"Content-Type": "application/json; charset=utf-8"}
    url = dataConfig["url-backend-shopp"] + "/venta_facturacion_id/" + empresa
    response = requests.get(url, headers=headers)
    return response.json()


@ventas.route("/delete_vent_id_empresa/<empresa>", methods=['DELETE'])
def delete_vent_by_id_empr(empresa):
    headers = {"Content-Type": "application/json; charset=utf-8"}
    url = dataConfig["url-backend-shopp"] + "/delete_facturacion_id/" + empresa
    response = requests.delete(url, headers=headers)
    return response.json()


# obtienes todas las facturas   ¡¡¡¡¡¡¡¡¡¡¡¡¡¡el usuario no tendra acceso a esta funcion!!!!!!!!!!!!!
@ventas.route("/vent_all", methods=['GET'])
def get_all_vent():
    headers = {"Content-Type": "application/json; charset=utf-8"}
    url = dataConfig["url-backend-shopp"] + "/facturas"
    response = requests.get(url, headers=headers)
    return response.json()
