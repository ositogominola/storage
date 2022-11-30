from datetime import datetime
from settings.database import db, BaseModel

class invoice(db.Model, BaseModel):
    __tablename__='invoice'
    __table_args__={'extend_existing': True}

    id=db.Column(db.Integer, primary_key=True)
    fecha=db.Column(db.DateTime)
    total=db.Column(db.Integer)
    empresa=db.Column(db.String(20))
    venta=db.relationship("venta", backref='invoice', cascade='delete')

    def __init__(self):
        self.fecha=self.datatime()

    def setEmpresa(self, empresa):
        self.empresa=empresa

    def datatime(self):
        return datetime.now()

    def setTotal(self, total):
        self.total=total
