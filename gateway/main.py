import requests
from flask import Flask, make_response, jsonify
from settings.loadFileConfig import loadFileConfig
from waitress import serve
from Routers.articulosrouters import articulos
from Routers.shopRouters import ventas
from Routers.factorysrouters import factory
import re
from flask import request


Gateway=Flask(__name__)
Gateway.register_blueprint(articulos)
Gateway.register_blueprint(ventas)
Gateway.register_blueprint(factory)


@Gateway.before_request
def verificar():
    # Obtener la información necesaria para realizar la solicitud HTTP
    url = 'http://127.0.0.1:9999/cnf/verificar_permisos'
    headers = {
        'Authorization': request.headers.get('Authorization'),
        'Content-Type': 'application/json'
    }
    data = {
        'method': request.method,
        'url': request.path
    }

    # Realizar la solicitud HTTP
    response = requests.get(url, headers=headers, json=data)
    # Si la solicitud es exitosa y el usuario tiene permisos, continuar con la solicitud original
    if response.status_code == 401:
        return make_response(jsonify({'message': 'no estas loggeado'}), 401)

    elif response.status_code == 200 and response.json().get('authorized', False):
        return None
    elif response.status_code == 500:
        return make_response(jsonify({'message': 'se presento '}), 500)
    # Si el usuario no tiene permisos, retornar una respuesta con un mensaje de error
    return make_response(jsonify({'message': 'No tiene permisos para acceder a este recurso'}), 403)


if __name__ == '__main__':
    dataConfig = loadFileConfig()
    print("Server running : " + "http://" + dataConfig["url-backend"] + ":" + str(dataConfig["port"]))
    serve(Gateway, host=dataConfig["url-backend"], port=dataConfig["port"])

