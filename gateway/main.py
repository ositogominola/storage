import requests
from flask import Flask, make_response, jsonify

from settings.loadFileConfig import loadFileConfig
from waitress import serve

from Routers.articulosrouters import articulos
from Routers.shopRouters import ventas
from Routers.factorysrouters import factory
from Routers.userRouters import user
from flask_cors import CORS
import re
from flask import request


Gateway=Flask(__name__)
CORS(Gateway, origins=['http://localhost:3000'], supports_credentials=True, allow_headers=["Authorization",    "Content-Type",    "Access-Control-Allow-Headers",    "Access-Control-Allow-Origin"])
Gateway.register_blueprint(articulos)
Gateway.register_blueprint(ventas)
Gateway.register_blueprint(factory)
Gateway.register_blueprint(user)


@Gateway.before_request
def verificar():
    if request.method == 'OPTIONS':
        print("saltar peticion")
        return None
    print("verificando permisos "+request.path)

    # Obtener la información necesaria para realizar la solicitud HTTP
    url = 'http://'+dataConfig["url-backend-security"]+'/cnf/verificar_permisos'
    headers = {
        'Authorization': request.headers.get('Authorization'),
        'Content-Type': 'application/json'
    }
    data = {
        'method': request.method,
        'url': request.path
    }
    if request.path == '/login':
        return None
    # Realizar la solicitud HTTP
    response = requests.get(url, headers=headers, json=data)
    # Si la solicitud es exitosa y el usuario tiene permisos, continuar con la solicitud original
    if response.status_code == 401:
        print("no esta loggeado")
        return make_response(jsonify({'message': 'no estas loggeado'}), 401)

    elif response.status_code == 200 and response.json().get('authorized', False):
        print("tiene permiso")
        return None
    elif response.status_code == 500:
        print("se perseto un error")
        return make_response(jsonify({'message': 'se presento '}), 500)
    # Si el usuario no tiene permisos, retornar una respuesta con un mensaje de error
    print("no tiene permisos")
    return make_response(jsonify({'message': 'No tiene permisos para acceder a este recurso'}), 403)


if __name__ == '__main__':
    dataConfig = loadFileConfig()
    print("Server running : " + "http://" + dataConfig["url-backend"] + ":" + str(dataConfig["port"]))
    Gateway.run(host=dataConfig["url-backend"], port=dataConfig["port"],debug=True)

