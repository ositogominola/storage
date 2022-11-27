from flask import Flask
from routes.ProductsRouter import Product
from schemas import Productschema
from settings.db import db
import json

app = Flask(__name__)

#configuracion database
app.config['SQLALCHEMY_DATABASE_URI'] = 'mysql+pymysql://root:1000579643@127.0.0.1:3306/productos'
app.config["SQLALCHEMY_TRACK_MODIFICATIONS"] = False

db.init_app(app)
schemaPr = Productschema.ProductSchema()

app.register_blueprint(Product)

"""with app.app_context():
    db.drop_all()
    db.create_all()"""

if __name__ == "__main__":
    app.run(debug=True, port=5555)
