from flask import Flask
from settings.database import *
from models import invoice, venta
from routers.ventasRoute import venta

app=Flask(__name__)

databasesetting(app)
db.init_app(app)

app.register_blueprint(venta)
"""with app.app_context():
    db.drop_all()
    db.create_all()"""

if __name__ == '__main__':
    app.run(host="0.0.0.0", debug=True, port=8080)

