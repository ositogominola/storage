from flask import Flask
from settings.database import *
from models import invoice, venta

app=Flask(__name__)

databasesetting(app)

db.init_app(app)

with app.app_context():
    db.drop_all()
    db.create_all()

if __name__ == '__main__':
    app.run(debug=True, port=8080)

