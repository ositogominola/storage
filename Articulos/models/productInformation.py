from settings.db import db, BaseModelMixin
from datetime import datetime

class infProduct(db.Model, BaseModelMixin):
    __tablename__ = "infProduct"
    __table_args__ = {'extend_existing': True}

    id = db.Column(db.Integer, primary_key=True)
    proveedor = db.Column(db.String(50))
    costoCompra = db.Column(db.Integer)
    costoVenta = db.Column(db.Integer)
    idEmpresa = db.Column(db.String(60))
    stock=db.Column(db.Integer)
    DataTime = db.Column(db.DateTime)
    idProducto = db.Column(db.Integer, db.ForeignKey('Producto.id'))

    """def __int__(self, idProducto, proveedor, costoCompra, costoVenta):
        self.idProducto = idProducto
        self.proveedor = proveedor
        self.costoCompra = costoCompra
        self.costoVenta = costoVenta"""

    def __int__(self, **datos):
        self.idProducto = datos["idProducto"]
        self.proveedor = datos["proveedor"]
        self.costoCompra = datos["costoCompra"]
        self.costoVenta = datos["costoVenta"]
        self.idEmpresa = datos["idEmpresa"]
        self.stock=datos["stock"]

    def datatime(self):
        self.DataTime=datetime.now()