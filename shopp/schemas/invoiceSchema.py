from models.invoice import invoice
from settings.ext import ma

class invoiceSchema(ma.SQLAlchemyAutoSchema):
    class Meta:
        model = invoice
