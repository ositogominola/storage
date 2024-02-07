from flask import Blueprint, request
import requests
from settings.loadFileConfig import loadFileConfig

factory = Blueprint("factory", __name__)
dataConfig = loadFileConfig()


@factory.route("/create_factory", methods=['POST'])
def create_factory():
    headers = {
        'Authorization': request.headers.get('Authorization'),
        "Content-Type": "application/json; charset=utf-8"}
    jsonOwn = request.get_json()
    url = dataConfig["url-backend-security"] + "/factory/create"
    reqsol = requests.post(url, headers=headers, json=jsonOwn)
    return reqsol.json()


@factory.route("/get_factorys", methods=['GET'])
def get_factorys_user():
    headers = {
        'Authorization': request.headers.get('Authorization'),
        "Content-Type": "application/json; charset=utf-8"}
    url = dataConfig["url-backend-security"] + "/factory/getall"
    reqsol = requests.get(url, headers=headers)
    return reqsol.json()


@factory.route("/get_by_id/<id>", methods=['GET'])
def get_factory_by_id(id):
    headers = {
        'Authorization': request.headers.get('Authorization'),
        "Content-Type": "application/json; charset=utf-8"}
    url = dataConfig["url-backend-security"] + "/factory/getByid/" + id
    reqsol = requests.get(url, headers=headers)
    return reqsol.json()


@factory.route("/update_factory/<id>", methods=['PATCH'])
def update_factory_by_id(id):
    headers = {
        'Authorization': request.headers.get('Authorization'),
        "Content-Type": "application/json; charset=utf-8"}
    url = dataConfig["url-backend-security"] + "/factory/update/" + id
    json = request.get_json()
    reqsol = requests.patch(url, headers=headers, json=json)
    return reqsol.json()


@factory.route("/delete_factory/<id>", methods=['DELETE'])
def delete_factory_by_id(id):
    if comprobar_empresa(id):
        headers = {
            'Authorization': request.headers.get('Authorization'),
            "Content-Type": "application/json; charset=utf-8"}
        articulos = "http://127.0.0.1:7777/articulo_id_company/" + id
        urlar = requests.get(articulos, headers=headers)
        print(urlar.json())
        if urlar.json()['successful']:
            for fbc in urlar.json()['Producto']:
                eliminar_articulos_empresa(fbc["id"], id)

        url = dataConfig["url-backend-security"] + "/factory/delete/" + id
        reqsol = requests.delete(url, headers=headers)
        return reqsol.json()
    else:
        return "no se encontro la empresa"



def eliminar_articulos_empresa(id, idfac):
    headers = {
        'Authorization': request.headers.get('Authorization'),
        "Content-Type": "application/json; charset=utf-8"}
    articulos = "http://127.0.0.1:7777//delete_art/" + id + "/factory/" + idfac
    urlar = requests.delete(articulos, headers=headers)

def comprobar_empresa(idempresa):
    headers = {
        'Authorization': request.headers.get('Authorization'),
        "Content-Type": "application/json; charset=utf-8"
    }
    verificarEmpresa = dataConfig["url-backend-security"] + "/factory/getByid/" + idempresa
    response = requests.get(verificarEmpresa, headers=headers)
    return response.json()["seccessful"]