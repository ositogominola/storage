from flask import Blueprint, request
import requests
from settings.loadFileConfig import loadFileConfig

articulos = Blueprint("articulos", __name__)
dataConfig = loadFileConfig()


# crea el articulo
@articulos.route("/create_art", methods=['POST'])
def create_art():
    data = request.get_json()
    headers = {"Content-Type": "application/json; charset=utf-8"}
    url = dataConfig["url-backend-Articulos"] + '/producto'
    response = requests.post(url, headers=headers, json=data)
    return response.json()


# elimina el articulo
@articulos.route("/delete_art/<int:id>", methods=['DELETE'])
def delete_Art(id):
    headers = {"Content-Type": "application/json; charset=utf-8"}
    url = dataConfig["url-backend-Articulos"] + '/producto/' + str(id)
    response = requests.delete(url, headers=headers)
    return response.json()


# actualiza el articulo
@articulos.route("/update_art/<int:id>", methods=['PATCH'])
def update_Art(id):
    headers = {"Content-Type": "application/json; charset=utf-8"}
    data = request.get_json()
    url = dataConfig["url-backend-Articulos"] + '/producto/update/' + str(id)
    response = requests.patch(url, headers=headers, json=data)
    return response.json()


# obtiene un articulo por el id
@articulos.route("/articulo_id/<id>", methods=['GET'])
def get_articulo_id(id):
    headers = {"Content-Type": "application/json; charset=utf-8"}
    url = dataConfig["url-backend-Articulos"] + '/producto/' + str(id)
    response = requests.get(url, headers=headers)
    return response.json()


# obtiene los articulos por empresa
@articulos.route("/articulo_id_company/<idEmpresa>", methods=['GET'])
def get_Articulo_Empre(idEmpresa):
    headers = {"Content-Type": "application/json; charset=utf-8"}
    url = dataConfig["url-backend-Articulos"] + '/producto/id/' + idEmpresa
    response = requests.get(url, headers=headers)
    return response.json()

# obtiene por un filtro especifico                  falta completar
@articulos.route("/articulo-filter/<idempresa>", methods=['GET'])
def articulosfilter(idempresa):
    headers = {"Content-Type": "application/json; charset=utf-8"}
    data = request.get_json()
    url = dataConfig["url-backend-Articulos"] + '/producto/filter/' + str(idempresa)
    response = requests.get(url, headers=headers, json=data)
    return response.json()














# obtiene todos los articulos                    !!!!!!!!!!!!!!el usuario no tendra acceso a esta funcion¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡
@articulos.route("/articulo", methods=['GET'])
def getarticulo():
    headers = {"Content-Type": "application/json; charset=utf-8"}
    url = dataConfig["url-backend-Articulos"] + '/producto'
    response = requests.get(url, headers=headers)
    return response.json()
