from flask import Blueprint, request, make_response, jsonify
import requests
from settings.loadFileConfig import loadFileConfig
from datetime import datetime, timedelta

user = Blueprint("user", __name__)
dataConfig = loadFileConfig()

@user.route("/create_user", methods=["POST"])
def create_user():
    user=request.get_json()
    headers = {"Content-Type": "application/json; charset=utf-8"}
    url=dataConfig["url-backend-security"]+"/user/create"
    return requests.post(url, headers=headers, json=user).json()

@user.route("/login", methods=["POST"])
def login():
    headers = {
        'Authorization': request.headers.get('Authorization'),
        "Content-Type": "application/json; charset=utf-8"
    }
    url = dataConfig["url-backend-security"] + "/login"
    token=requests.post(url, headers=headers)
    data={}
    if token.status_code==200:
        # Crea una respuesta
        res = make_response()

        # Añade la cookie HttpOnly con el token
        expira = datetime.now()
        expira = expira + timedelta(days=1)

        res.set_cookie('token', token.text, expires=expira, httponly=True, samesite="Lax")

        url = dataConfig["url-backend-security"] + "/user"
        headers = {
            'Authorization': "Bearer "+token.text,
            "Content-Type": "application/json; charset=utf-8"
        }
        user=requests.get(url, headers=headers)
        if user.status_code==200:
            data["user"]=user.json()["user"];
        data["succesfull"] = True
        res.set_data(jsonify(data).get_data())
    else:
        data["succesfull"] = False
        return data
    return res

@user.route("/logoutUser", methods=["POST"])
def logout():
    res = make_response("Has cerrado la sesión")
    res.set_cookie('token', "cerrado", max_age=0, httponly=True, samesite="Lax")
    return res

@user.route("/isAuthenticated", methods=["GET"])
def isAuthenticated():
    token=request.cookies.get("token")
    if token==None:
        return "El usuario no está autenticado", 401

    headers = {
        "Content-Type": "application/json; charset=utf-8"
    }
    url = dataConfig["url-backend-security"] + "/isAuthenticated"
    response = requests.get(url, headers=headers, cookies={'token': token})
    # Comprobar el código de estado de la respuesta
    if response.status_code == 200:
        return "El usuario está autenticado", 200
    else:
        return "El usuario no está autenticado", 401


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