import requests
from flask import Flask, make_response, jsonify

from settings.loadFileConfig import loadFileConfig

from Routers.articulosrouters import articulos
from Routers.shopRouters import ventas
from Routers.factorysrouters import factory
from Routers.userRouters import user
from flask_cors import CORS
from flask import request
import logging

#logging.basicConfig(level=logging.DEBUG)
#logger = logging.getLogger('flask_cors')
#logger.setLevel(logging.DEBUG)


Gateway=Flask(__name__)
CORS(Gateway, origins=['http://127.0.0.1:5173', 'http://127.0.0.1:5173/'], supports_credentials=True)
Gateway.register_blueprint(articulos)
Gateway.register_blueprint(ventas)
Gateway.register_blueprint(factory)
Gateway.register_blueprint(user)

from werkzeug.datastructures import Headers

@Gateway.before_request
def verificar():
    if request.method == 'OPTIONS':
        token = request.cookies.get('token')
        return 'OPTIONS', 204

    # Obtener la información necesaria para realizar la solicitud HTTP
    url = dataConfig["url-backend-security"]+'/cnf/verificar_permisos'
    token = request.cookies.get('token')

    # Configurar las cabeceras
    headers = {
        'Authorization': f'Bearer {token}',
        'Content-Type': 'application/json'
    }

    data = {
        'method': request.method,
        'url': request.path
    }
    if request.path == '/login' or request.path=='/isAuthenticated':
        return None
    elif request.path == '/logoutUser':
        new_headers = Headers(request.headers)
        new_headers.add('Authorization', f'Bearer {token}')
        request.headers = new_headers
        return None

    response = requests.get(url, headers=headers, json=data)
    if response.status_code == 401:
        return make_response('no estas loggeado', 401)
    elif response.status_code == 200 and response.json().get('authorized', False):
        new_headers = Headers(request.headers)
        new_headers.add('Authorization',f'Bearer {token}')
        request.headers = new_headers
        return None
    elif response.status_code == 500:
        return make_response('se presento un error en el servidor', 500)
    return make_response('No tiene permisos para acceder a este recurso', 403)


@Gateway.route("/updatePermision", methods=["PUT"])
def updatePermision():
    print("rutas..........")
    headers = {
        "Content-Type": "application/json; charset=utf-8"
    }
    data=""
    url = dataConfig["url-backend-security"] + "/user/updatePermision"
    return requests.put(url, headers=headers).json()


if __name__ == '__main__':
    dataConfig = loadFileConfig()
    print("Server running : " + "http://" + dataConfig["url-backend"] + ":" + str(dataConfig["port"]))
    Gateway.run(host=dataConfig["url-backend"], port=dataConfig["port"],debug=True)