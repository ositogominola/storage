from settings.ext import ma
# from marshmallow import fields
from models.Product import Producto
from .InfProducSchema import InfProductoShema

class ProductSchema(ma.SQLAlchemyAutoSchema):
    class Meta:
        model=Producto

    prdinfo = ma.Nested(InfProductoShema,exclude=("id",))