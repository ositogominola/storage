from models.invoice import invoice
from settings.ext import ma
from .ventaSchema import ventaSchema
class invoiceSchema(ma.SQLAlchemyAutoSchema):
    class Meta:
        model = invoice

    venta=ma.Nested(ventaSchema(many=True))
