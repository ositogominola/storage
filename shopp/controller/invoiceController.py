from models.invoice import invoice
from schemas.invoiceSchema import invoiceSchema

class invoiceController():
    schemainv=invoiceSchema()
    schemasinv = invoiceSchema(many=True)
    model = invoice()

    #crea y guarda la factura
    def POST(self):
        dicti = {}
        try:
            invoiceU = invoice()
            invoiceU.datatime()
            invoiceU.save()
            ventaProduc = self.schemainv.dump(invoiceU)
            successful = True
            message = "factura creada"
        except Exception as e:
            ventaProduc = None
            successful = False
            message = "ERROR {}, TYPE {}".format(e.args, type(e))
        dicti["ventaProduc"] = ventaProduc
        dicti["successful"] = successful
        dicti["message"] = message
        return dicti

    #actualiza el total y da la empresa dueña
    def UPDATE_TOTAL(self,id,total,empresa):
        dicti = {}
        try:
            facturaUn=invoice.get_by_id(id)
            facturaUn.setTotal(total)
            facturaUn.setEmpresa(empresa)
            facturaUn.save()
            facturaPro = self.schemainv.dump(facturaUn)
            successful = True
            message = "factura creada"
        except Exception as e:
            facturaPro = None
            successful = False
            message = "ERROR {}, TYPE {}".format(e.args, type(e))
        dicti["facturaPro"] = facturaPro
        dicti["successful"] = successful
        dicti["message"] = message
        return dicti

    #obtiene todas las facturas
    def get_all(self):
        data={}
        try:
            facturas=self.model.get_all()
            if facturas is None:
                FCT = None
                successful = False
                message = "no hay productos registrados"
            else:
                FCT = self.schemasinv.dump(facturas)
                successful = True
                message = "facturas cargadas"
        except Exception as e:
            FCT = None
            successful = False
            message = "ERROR {}, TYPE {}".format(e.args, type(e))
        data["FCT"]=FCT
        data["successful"]=successful
        data["message"]=message
        return data

    #obtiene las facturas por empresa
    def get_all_by_id_empr(self,empresa):
        data = {}
        try:
            facturas = self.model.simple_filter(empresa=empresa)
            if facturas is None:
                FCT = None
                successful = False
                message = "no hay productos registrados"
            else:
                FCT = self.schemasinv.dump(facturas)
                successful = True
                message = "facturas cargadas"
        except Exception as e:
            FCT = None
            successful = False
            message = "ERROR {}, TYPE {}".format(e.args, type(e))
        data["FCT"] = FCT
        data["successful"] = successful
        data["message"] = message
        return data

    #elimina una factura con todos los productos que esta tenga
    def delete_by_id(self,id):
        dicti = {}
        try:
            factura = self.model.get_by_id(id=id)
            print(factura)
            if factura is None:
                successful = False
                message = "la factura bajo el id {} no existe".format(id)
            else:
                factura.delete()
                successful = True
                message = "la factura bajo el id {} fue eliminado correctamente".format(id)
        except Exception as e:
            successful = False
            message = "ERROR: {}, TYPE: {}".format(e.args, type(e))
        dicti["successful"] = successful
        dicti["message"] = message
        return dicti
