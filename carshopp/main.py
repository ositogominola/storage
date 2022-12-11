from flask import Flask
from routers.genericRouters import generic

app=Flask(__name__)

app.register_blueprint(generic)

if __name__ == '__main__':
    app.run(debug=True,port=5555)

