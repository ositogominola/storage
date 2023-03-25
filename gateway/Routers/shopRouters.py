from flask import Blueprint, request
import requests
from settings.loadFileConfig import loadFileConfig

ventas = Blueprint("ventas", __name__)
dataConfig = loadFileConfig()



# crear venta con facturas (se puede comprar varios articulos a la vez)
@ventas.route("/create_vent", methods=['POST'])
def create_vent():
    responseow = {}

    # comprobar si existe el producto
    headers = {
        'Authorization':request.headers.get('Authorization'),
        "Content-Type": "application/json; charset=utf-8"}
    data = request.get_json()
    url = "http://" + dataConfig["url-backend"] + ":" + str(dataConfig["port"])
    dontSave = []
    for prd in data["prd"]:

        if data["empresa"] == prd["idempresa"]:
            urlp = url + "/articulo_id/" + str(prd["idproducto"])+"/fact/"+str(prd["idempresa"])
            json = {"idEmpresa": prd["idempresa"]}
            response = requests.get(urlp, headers=headers, json=json)
            if response.json()["successful"]:
                producto = response.json()["Producto"]
                if producto is None:
                    dontSave.append(prd)
                else:
                    if int(producto["prdinfo"]["stock"]) <= prd["cantidad"]:
                        responseow["except"]=str("el pedido supera el stock PRODUCTO: {}".format(producto["name"]))
                        dontSave.append(prd)
                    prd["precioUn"] = producto["prdinfo"]["costoVenta"]
            else:
                dontSave.append(prd)
        else:
            dontSave.append(prd)
    for dat in dontSave:
        data["prd"].remove(dat)

    if data["prd"]:
        url = dataConfig["url-backend-shopp"] + "/venta_create/"+data["empresa"]
        response = requests.post(url, headers=headers, json=data)
        datos=response.json()
        successful=True
        message="datos creados exitosamente"
    else:
        datos = None
        successful = False
        message = "no se pudo registrar los datos"

    responseow["datos"]=datos
    responseow["successful"]=successful
    responseow["message"]=message
    return responseow


#        obtienen uno solo

# obtiene un producto vendido por id
@ventas.route("/vent_prod_id/<id>/empr/<idEmpr>", methods=['GET'])
def get_vent_by_id(id, idEmpr):
    headers = {"Content-Type": "application/json; charset=utf-8"}
    if comprobar_empresa(idEmpr):
        url = dataConfig["url-backend-shopp"] + "/venta_product_id/" + id +"/empr/"+idEmpr
        response = requests.get(url, headers=headers)
    else:
        return "False"
    return response.json()


# obtiene una venta en facturacion de la empresa por su id
@ventas.route("/vent_fact_id/<id>/empr/<empresa>", methods=['GET'])
def get_fact_pro_by_id_emp(id, empresa):
    headers = {"Content-Type": "application/json; charset=utf-8"}
    if comprobar_empresa(empresa):
        url = dataConfig["url-backend-shopp"] + "/get_only_fact_id/" + id + "/empr/"+empresa
        response = requests.get(url, headers=headers)
    else:
        return "False"
    return response.json()


#               obtiene todos

# obtiene todos los productos vendidos por empresa
@ventas.route("/vent_prod_id_empr/<id>", methods=['GET'])
def get_vent_pro_by_id_emp(id):
    headers = {"Content-Type": "application/json; charset=utf-8"}
    if comprobar_empresa(id):
        url = dataConfig["url-backend-shopp"] + "/get_prod_id_em/" + id
        response = requests.get(url, headers=headers)
    else:
        return "False"
    return response.json()


# obtiene todas las facturas por id de empresas
@ventas.route("/vent_id_empresa/<empresa>", methods=['GET'])
def get_vent_by_id_empr(empresa):
    headers = {"Content-Type": "application/json; charset=utf-8"}
    if comprobar_empresa(empresa):
        url = dataConfig["url-backend-shopp"] + "/venta_facturacion_id/" + empresa
        response = requests.get(url, headers=headers)
    else:
        return "False"
    return response.json()


@ventas.route("/delete_vent_id_empresa/<id>/empr/<idEmp>", methods=['DELETE'])
def delete_vent_by_id_empr(id,idEmp):
    headers = {"Content-Type": "application/json; charset=utf-8"}
    if comprobar_empresa(idEmp):
        url = dataConfig["url-backend-shopp"] + "/delete_facturacion_id/" + id+"/empr/"+idEmp
        response = requests.delete(url, headers=headers)
    else:
        return "False"
    return response.json()


# obtienes todas las facturas   ¡¡¡¡¡¡¡¡¡¡¡¡¡¡el usuario no tendra acceso a esta funcion!!!!!!!!!!!!!
@ventas.route("/vent_all", methods=['GET'])
def get_all_vent():
    headers = {"Content-Type": "application/json; charset=utf-8"}
    url = dataConfig["url-backend-shopp"] + "/facturas"
    response = requests.get(url, headers=headers)
    return response.json()







def comprobar_empresa(idempresa):
    headers = {
        'Authorization': request.headers.get('Authorization'),
        "Content-Type": "application/json; charset=utf-8"
    }
    verificarEmpresa = dataConfig["url-backend-security"] + "/factory/getByid/" + idempresa
    response = requests.get(verificarEmpresa, headers=headers)
    return response.json()["seccessful"]
