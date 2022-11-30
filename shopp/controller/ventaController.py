from models.venta import venta
from schemas.ventaSchema import ventaSchema

class ventaController:

    schemaVnt=ventaSchema()
    schemasVnt = ventaSchema(many=True)

    def POST(self, data):
        dicti={}
        try:
            ventaProd=venta(**data)
            ventaProd.precio_total()
            ventaProd.save()
            ventaProduc = self.schemaVnt.dump(ventaProd)
            successful = True
            message = "producto añadido a la compra"
        except Exception as e:
            ventaProduc = None
            successful = False
            message = "ERROR {}, TYPE {}".format(e.args, type(e))
        dicti["ventaProduc"]=ventaProduc
        dicti["successful"]=successful
        dicti["message"]=message
        return dicti

    def GET_ALL(self):
        return self.schemasVnt.dump(venta.get_all())

    def GET_BY_ID(self,id):
        return self.schemaVnt.dump(venta.get_by_id(id))