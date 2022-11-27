from settings.db import db, BaseModelMixin
from models.productInformation import infProduct

class Producto(db.Model, BaseModelMixin):
    __tablename__='Producto'
    __table_args__ = {'extend_existing': True}

    id=db.Column(db.Integer, primary_key=True)
    name=db.Column(db.String(30))
    image=db.Column(db.String(50))
    details=db.Column(db.String(150))
    prdinfo=db.relationship("infProduct", backref='Producto', uselist=False, cascade='delete')

    """def __int__(self, name, stock, details):
        self.stock=stock
        self.name=name
        self.details=details"""

    def __int__(self, **datos):
        self.image=datos["image"]
        self.name=datos["name"]
        self.details=datos["details"]
