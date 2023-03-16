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
    jsonOwn=request.get_json()
    url = dataConfig["url-backend-security"] + "/factory/create"
    reqsol=requests.post(url, headers=headers, json=jsonOwn)
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
    url = dataConfig["url-backend-security"] + "/factory/getByid/"+id
    reqsol = requests.get(url, headers=headers)
    return reqsol.json()


@factory.route("/update_factory/<id>", methods=['PATCH'])
def update_factory_by_id(id):
    headers = {
        'Authorization': request.headers.get('Authorization'),
        "Content-Type": "application/json; charset=utf-8"}
    url = dataConfig["url-backend-security"] + "/factory/update/"+id
    json=request.get_json()
    reqsol = requests.patch(url, headers=headers,json=json)
    return reqsol.json()


@factory.route("/delete_factory/<id>", methods=['DELETE'])
def delete_factory_by_id(id):
    headers = {
        'Authorization': request.headers.get('Authorization'),
        "Content-Type": "application/json; charset=utf-8"}
    url = dataConfig["url-backend-security"] + "/factory/delete/"+id
    reqsol = requests.delete(url, headers=headers)
    return reqsol.json()