from datetime import datetime
from settings.database import db, BaseModel

class invoice(db.Model, BaseModel):
    __tablename__='invoice'
    __table_args__={'extend_existing': True}

    id=db.Column(db.Integer, primary_key=True)
    fecha=db.Column(db.DateTime)
    total=db.Column(db.Integer)
    venta=db.relationship("venta", backref='invoice')

    def __init__(self):
        self.fecha=self.datatime()

    def datatime(self):
        return datetime.now()
