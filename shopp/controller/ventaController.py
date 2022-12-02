from models.venta import venta
from schemas.ventaSchema import ventaSchema

class ventaController:

    schemaVnt=ventaSchema()
    schemasVnt = ventaSchema(many=True)
    model=venta()

    #crea la compra de un producto
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

    #obtiene todas las comprass
    def GET_ALL(self):
        data = {}
        try:
            facturas = self.model.get_all()
            if facturas is None:
                PRT = None
                successful = False
                message = "no hay productos registrados"
            else:
                PRT = self.schemasVnt.dump(facturas)
                successful = True
                message = "facturas cargadas"
        except Exception as e:
            PRT = None
            successful = False
            message = "ERROR {}, TYPE {}".format(e.args, type(e))
        data["PRT"] = PRT
        data["successful"] = successful
        data["message"] = message
        return data

    #obtiene compras por id
    def GET_BY_ID(self,id):
        data = {}
        try:
            facturas = self.model.get_by_id(id)
            if facturas is None:
                PRT = None
                successful = False
                message = "no hay productos registrados"
            else:
                PRT = self.schemasVnt.dump(facturas)
                successful = True
                message = "facturas cargadas"
        except Exception as e:
            PRT = None
            successful = False
            message = "ERROR {}, TYPE {}".format(e.args, type(e))
        data["PRT"] = PRT
        data["successful"] = successful
        data["message"] = message
        return data

    #elimina la compra de un producto
    def DELETE(self, id):
        dicti = {}
        try:
            productoCompra = self.model.get_by_id(id)
            print(productoCompra)
            if productoCompra is None:
                successful = False
                message = "el producto bajo el id {} no existe".format(id)
            else:
                productoCompra.delete()
                successful = True
                message = "el producto bajo el id {} fue eliminado correctamente".format(id)
        except Exception as e:
            successful = False
            message = "ERROR: {}, TYPE: {}".format(e.args, type(e))
        dicti["successful"] = successful
        dicti["message"] = message
        return dicti
