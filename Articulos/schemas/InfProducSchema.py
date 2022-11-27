from settings.ext import ma
# from marshmallow import fields
from models.productInformation import infProduct
#from .Productschema import ProductSchema

class InfProductoShema(ma.SQLAlchemyAutoSchema):
    class Meta:
        model = infProduct

    #idProducto=ma.Nested(ProductSchema, many=True)