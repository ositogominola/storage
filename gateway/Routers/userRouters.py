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


@user.route("/addPermission/<id>/rol/<idrol>", methods=["POST"])
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

@user.route("/addPerfilItemUser/<idUser>/idPerfil/<idPerfil>", methods=["PUT"])
def addPerfilItem(idUser, idPerfil):
    headers = {
        'Authorization': request.headers.get('Authorization'),
        "Content-Type": "application/json; charset=utf-8"
    }
    url = dataConfig["url-backend-security"] + "/user/AddPerfilItemUser/" + idUser + "/Perfil/" + idPerfil
    return requests.put(url, headers=headers).json()

@user.route("/getPerfiles", methods=["GET"])
def getPerfilesUser():
    headers = {
        'Authorization': request.headers.get('Authorization'),
        "Content-Type": "application/json; charset=utf-8"
    }
    url = dataConfig["url-backend-security"] + "/user/PerfilesUser"
    return requests.get(url, headers=headers).json()

@user.route("/getRecursosPerfil/<idPerfil>/rol/<idRol>", methods=["GET"])
def getPerfilesPermisos(idPerfil, idRol):
    headers = {
        'Authorization': request.headers.get('Authorization'),
        "Content-Type": "application/json; charset=utf-8"
    }
    url = dataConfig["url-backend-security"] + "/user/PermisosPerfilItem/"+idPerfil+"/rol/"+idRol
    return requests.get(url, headers=headers).json()

@user.route("/getRoles",methods=["GET"])
def getRolesAll():
    headers = {
        'Authorization': request.headers.get('Authorization'),
        "Content-Type": "application/json; charset=utf-8"
    }
    url = dataConfig["url-backend-security"] + "/user/getRoles"
    return requests.get(url, headers=headers).json()


@user.route("/getPerfilesRoles/<idRol>", methods=["GET"])
def getPermissionRol(idRol):
    headers = {
        'Authorization': request.headers.get('Authorization'),
        "Content-Type": "application/json; charset=utf-8"
    }
    url = dataConfig["url-backend-security"] + "/user/getPerfilesRol/"+idRol
    return requests.get(url, headers=headers).json()

@user.route("/getRecursosRolesFalt/<idRol>/perfil/<idPerfil>", methods=["GET"])
def getPermissionRolFaltantes(idRol, idPerfil):
    headers = {
        'Authorization': request.headers.get('Authorization'),
        "Content-Type": "application/json; charset=utf-8"
    }
    url = dataConfig["url-backend-security"] + "/user/getRecursosFaltantesRol/"+idRol+"/perfil/"+idPerfil
    return requests.get(url, headers=headers).json()

@user.route("/deletePermisoRol/<idPer>/rol/<idRol>", methods=["DELETE"])
def deletePermisoRol(idPer, idRol):
    headers = {
        'Authorization': request.headers.get('Authorization'),
        "Content-Type": "application/json; charset=utf-8"
    }
    url = dataConfig["url-backend-security"] + "/user/deletePermiso/" + idPer+"/rol/"+idRol
    return requests.delete(url, headers=headers).json()

@user.route("/getAllPermission", methods=["GET"])
def getAllPermisoRol():
    headers = {
        'Authorization': request.headers.get('Authorization'),
        "Content-Type": "application/json; charset=utf-8"
    }
    url = dataConfig["url-backend-security"] + "/user/getAllPermission"
    return requests.get(url, headers=headers).json()

@user.route("/deletePermission/<idPer>", methods=["DELETE"])
def deletePermission(idPer):
    headers = {
        'Authorization': request.headers.get('Authorization'),
        "Content-Type": "application/json; charset=utf-8"
    }
    url = dataConfig["url-backend-security"] + "/user/deletePermision/"+idPer
    return requests.delete(url, headers=headers).json()

@user.route("/getAllUsers", methods=["GET"])
def getAllUsera():
    headers = {
        'Authorization': request.headers.get('Authorization'),
        "Content-Type": "application/json; charset=utf-8"
    }
    url = dataConfig["url-backend-security"] + "/user/getAllUsers"
    return requests.get(url, headers=headers).json()

@user.route("/getRecursosPerfilItem/<idPerfil>/rol/<idRol>", methods=["GET"])
def getRecursosPerfil(idPerfil, idRol):
    headers = {
        'Authorization': request.headers.get('Authorization'),
        "Content-Type": "application/json; charset=utf-8"
    }
    url = dataConfig["url-backend-security"] + "/user/getRecusosPerfilItem/"+idPerfil+"/rol/"+idRol
    return requests.get(url, headers=headers).json()


@user.route("/getRecursosPermisos/<idRecurso>/rol/<idRol>", methods=["GET"])
def getRecursosPermisos(idRecurso, idRol):
    headers = {
        'Authorization': request.headers.get('Authorization'),
        "Content-Type": "application/json; charset=utf-8"
    }
    url = dataConfig["url-backend-security"] + "/user/getRecusosPermisos/"+idRecurso+"/rol/"+idRol
    return requests.get(url, headers=headers).json()

@user.route("/getPerfilesRolFaltante/<idRol>", methods=["GET"])
def getPerfilesRolFaltante(idRol):
    headers = {
        'Authorization': request.headers.get('Authorization'),
        "Content-Type": "application/json; charset=utf-8"
    }
    url = dataConfig["url-backend-security"] + "/user/getPerfilesRolFaltante/"+idRol
    return requests.get(url, headers=headers).json()

@user.route("/deleteRol/<idRol>", methods=["DELETE"])
def deleteRol(idRol):
    headers = {
        'Authorization': request.headers.get('Authorization'),
        "Content-Type": "application/json; charset=utf-8"
    }
    url = dataConfig["url-backend-security"] + "/user/deleteRol/" + idRol
    return requests.delete(url, headers=headers).json()

@user.route("/addPerfilRol/<idRol>/perfil/<idPerfil>", methods=["POST"])
def addPerfilRol(idRol, idPerfil):
    headers = {
        'Authorization': request.headers.get('Authorization'),
        "Content-Type": "application/json; charset=utf-8"
    }
    url = dataConfig["url-backend-security"] + "/user/addPerfilRol/" + idRol + "/perfil/"+idPerfil
    return requests.post(url, headers=headers).json()

@user.route("/deletePerfilRol/<idPerfil>/rol/<idRol>", methods=["DELETE"])
def deletePerilRol(idPerfil, idRol):
    headers = {
        'Authorization': request.headers.get('Authorization'),
        "Content-Type": "application/json; charset=utf-8"
    }
    url = dataConfig["url-backend-security"] + "/user/deletePerfilRol/" + idPerfil+"/rol/"+idRol
    return requests.delete(url, headers=headers).json()

@user.route("/deleteRecursosRol/<idRecurso>/rol/<idRol>", methods=["DELETE"])
def deleteRecursoRol(idRecurso, idRol):
    headers = {
        'Authorization': request.headers.get('Authorization'),
        "Content-Type": "application/json; charset=utf-8"
    }
    url = dataConfig["url-backend-security"] + "/user/deleteRecurso/" + idRecurso+"/rol/"+idRol
    return requests.delete(url, headers=headers).json()

@user.route("/addRecursoRol/<idRecurso>/rol/<idRol>", methods=["POST"])
def addRecursoRol(idRecurso, idRol):
    headers = {
        'Authorization': request.headers.get('Authorization'),
        "Content-Type": "application/json; charset=utf-8"
    }
    url = dataConfig["url-backend-security"] + "/user/addRecursoRol/" + idRecurso + "/rol/"+idRol
    return requests.post(url, headers=headers).json()

@user.route("/getPermisosRecursoRolFaltante/<idRecurso>/rol/<idRol>", methods=["GET"])
def getPermisosRecursoRolFaltante(idRecurso,idRol):
    headers = {
        'Authorization': request.headers.get('Authorization'),
        "Content-Type": "application/json; charset=utf-8"
    }
    url = dataConfig["url-backend-security"] + "/user/getPermisosRecursosRolFaltante/"+idRecurso+"/rol/"+idRol
    return requests.get(url, headers=headers).json()