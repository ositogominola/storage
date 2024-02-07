from flask import Blueprint, request
import requests
from settings.loadFileConfig import loadFileConfig

articulos = Blueprint("articulos", __name__)
dataConfig = loadFileConfig()


# crea el articulo
@articulos.route("/create_art", methods=['POST'])
def create_art():
    data = request.get_json()
    headers = {
        "Content-Type": "application/json; charset=utf-8"
    }
    url = dataConfig["url-backend-Articulos"] + '/producto'
    validFabri=comprobar_empresa(data["infoPro"]["idEmpresa"])
    if validFabri:
        for value in data['infoPro'].keys():
            if value=="costoCompra":
                data['infoPro']["costoCompra"]=int(data['infoPro']["costoCompra"])
            if value=="costoVenta":
                data['infoPro']["costoVenta"] = int(data['infoPro']["costoVenta"])
            if value=="stock":
                data['infoPro']["stock"] = int(data['infoPro']["stock"])
        response = requests.post(url, headers=headers, json=data)
        print(response.json())
        return response.json()
    else:
        return "no se puede registrar el producto por que no se encontro la empresa"



# elimina el articulo
@articulos.route("/delete_art/<id>/factory/<idfa>", methods=['DELETE'])
def delete_Art(id,idfa):
    headers = {"Content-Type": "application/json; charset=utf-8"}
    url = dataConfig["url-backend-Articulos"] + '/producto/' + str(id)
    json = {"idEmpresa":idfa}
    validFabri = comprobar_empresa(idfa)
    if validFabri:
        response = requests.delete(url, headers=headers, json=json)
        return response.json()
    else:
        return "no se encuentra el producto porfavor trate de nuevo"


# actualiza el articulo
@articulos.route("/update_art/<id>", methods=['PATCH'])
def update_Art(id):
    headers = {"Content-Type": "application/json; charset=utf-8"}
    data = request.get_json()
    url = dataConfig["url-backend-Articulos"] + '/producto/update/' + str(id)
    validFabri = comprobar_empresa(data["infoPro"]["idEmpresa"])
    if validFabri:
        response = requests.patch(url, headers=headers, json=data)
        return response.json()
    else:
        return "no se puede actualizar la empresa"



# obtiene un articulo por el id
@articulos.route("/articulo_id/<id>/fact/<idEmp>", methods=['GET'])
def get_articulo_id(id,idEmp):
    headers = {"Content-Type": "application/json; charset=utf-8"}
    url = dataConfig["url-backend-Articulos"] + '/producto/' + str(id)
    json = {"idEmpresa":idEmp}
    validFabri = comprobar_empresa(json["idEmpresa"])
    if validFabri:
        response = requests.get(url, headers=headers, json=json)
        return response.json()
    else:
        return {"successful":False}


# obtiene los articulos por empresa
@articulos.route("/articulo_id_company/<idEmpresa>", methods=['GET'])
def get_Articulo_Empre(idEmpresa):
    headers = {"Content-Type": "application/json; charset=utf-8"}
    url = dataConfig["url-backend-Articulos"] + '/producto/id/' + idEmpresa
    validFabri = comprobar_empresa(idEmpresa)
    if validFabri:
        response = requests.get(url, headers=headers)
        return response.json()
    else:
        return "no se encontro la empresa o no tiene permiso para acceder a ella"

# obtiene por un filtro especifico                  falta completar
@articulos.route("/articulo-filter/<idempresa>/name/<busqueda>", methods=['GET'])
def articulosfilter(idempresa, busqueda):
    headers = {"Content-Type": "application/json; charset=utf-8"}
    data = {'name':busqueda}
    url = dataConfig["url-backend-Articulos"] + '/producto/filter/' + str(idempresa)
    response = requests.get(url, headers=headers, json=data)
    return response.json()







def comprobar_empresa(idempresa):
    headers = {
        'Authorization': request.headers.get('Authorization'),
        "Content-Type": "application/json; charset=utf-8"
    }
    verificarEmpresa = dataConfig["url-backend-security"] + "/factory/getByid/" + idempresa
    response = requests.get(verificarEmpresa, headers=headers)
    return response.json()["seccessful"]






# obtiene todos los articulos                    !!!!!!!!!!!!!!el usuario no tendra acceso a esta funcion¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡
@articulos.route("/articulo", methods=['GET'])
def getarticulo():
    headers = {"Content-Type": "application/json; charset=utf-8"}
    url = dataConfig["url-backend-Articulos"] + '/producto'
    response = requests.get(url, headers=headers)
    return response.json()
