from flask import Flask
from flask_uuid import FlaskUUID

from routes.ProductsRouter import Product
from settings.db import db
from dotenv import load_dotenv
import os
import json

load_dotenv()

app = Flask(__name__)

#configuracion database
app.config['SQLALCHEMY_DATABASE_URI'] =  f"mysql+pymysql://{os.getenv('DB_USER')}:{os.getenv('DB_PASSWORD')}@{os.getenv('DB_HOST')}:{os.getenv('DB_PORT')}/{os.getenv('DB_NAME')}"
app.config['SQLALCHEMY_TRACK_MODIFICATIONS'] = False
app.config["SQLALCHEMY_TRACK_MODIFICATIONS"] = False

FlaskUUID(app)
db.init_app(app)

app.register_blueprint(Product)

"""with app.app_context():
    db.drop_all()
    db.create_all()"""

if __name__ == "__main__":
    app.run(host="0.0.0.0" , port=5555 ,debug=True)
