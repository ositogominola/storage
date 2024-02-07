from flask import Blueprint, request
import requests
from settings.loadFileConfig import loadFileConfig

user = Blueprint("user", __name__)
dataConfig = loadFileConfig()

@user.route("/create_user", methods=["POST"])
def create_user():
    user=request.get_json()
    headers = {"Content-Type": "application/json; charset=utf-8"}
    url=dataConfig["url-backend-security"]+"/user/create"
    return requests.post(url, headers=headers, json=user).json()

@user.route("/login", methods=["POST", "GET"])
def login():
    print("loggin")
    headers = {
        'Authorization': request.headers.get('Authorization'),
        "Content-Type": "application/json; charset=utf-8"
    }
    url = dataConfig["url-backend-security"] + "/login"
    token=requests.post(url, headers=headers)
    data={}
    if token.status_code==200:
        data["token"]=token.text
        data["succesfull"]=True
    else:
        data["succesfull"] = False
    return data

@user.route("/addPermission/<id>/idrol/<idrol>", methods=["POST"])
def addPermision(id, idrol):
    headers = {
        'Authorization': request.headers.get('Authorization'),
        "Content-Type": "application/json; charset=utf-8"
    }
    url = dataConfig["url-backend-security"] +"/user/addpermission/"+id+"/rol/"+idrol
    return requests.post(url,headers=headers).json()

@user.route("/create_role", methods=["POST"])
def createRol():
    rol=request.get_json()
    headers = {
        'Authorization': request.headers.get('Authorization'),
        "Content-Type": "application/json; charset=utf-8"
    }
    url=dataConfig["url-backend-security"] +"/user/createRole"
    return requests.post(url,headers=headers, json=rol).json()

@user.route("/create_permission", methods=["POST"])
def createPermission():
    rol=request.get_json()
    headers = {
        'Authorization': request.headers.get('Authorization'),
        "Content-Type": "application/json; charset=utf-8"
    }
    url=dataConfig["url-backend-security"] +"/user/createPermissions"
    return requests.post(url,headers=headers, json=rol).json()

@user.route("/addRol/<idrol>/us/<idus>", methods=["PUT"])
def addRol(idrol, idus):
    headers = {
        'Authorization': request.headers.get('Authorization'),
        "Content-Type": "application/json; charset=utf-8"
    }
    url=dataConfig["url-backend-security"] +"/user/rol/"+idrol+"/us/"+idus
    return requests.put(url, headers=headers).json()