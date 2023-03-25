from settings.database import db , BaseModel
import uuid
class venta(db.Model, BaseModel):
    __tablename__='venta'
    __table_args__ = {'extend_existing': True}

    id=db.Column(db.String(36), primary_key=True, default=uuid.uuid4)
    idproducto=db.Column(db.String(50))
    idempresa=db.Column(db.String(36))
    idfactura=db.Column(db.String(36), db.ForeignKey('invoice.id'))
    cantidad=db.Column(db.Integer)
    precioUn=db.Column(db.Integer)
    precioTot=db.Column(db.Integer)

    def __int__(self, **data):
        self.idproducto=data["idproducto"]
        self.idempresa=data["idempresa"]
        self.idfactura=data["idfactura"]
        self.cantidad=data["cantidad"]
        self.precioUn=data["precioUn"]

    def precio_total(self):
        self.precioTot=self.cantidad*self.precioUn
