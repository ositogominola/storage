from models.venta import venta
from settings.ext import ma


class ventaSchema(ma.SQLAlchemyAutoSchema):
    class Meta:
        model = venta
