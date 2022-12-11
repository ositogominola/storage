from flask import Flask
from settings.loadFileConfig import loadFileConfig
from waitress import serve
from Routers.articulosrouters import articulos
from Routers.shopRouters import ventas

Gateway=Flask(__name__)
Gateway.register_blueprint(articulos)
Gateway.register_blueprint(ventas)

if __name__ == '__main__':
    dataConfig = loadFileConfig()
    print("Server running : " + "http://" + dataConfig["url-backend"] + ":" + str(dataConfig["port"]))
    serve(Gateway, host=dataConfig["url-backend"], port=dataConfig["port"])

