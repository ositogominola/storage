from models.invoice import invoice
from schemas.invoiceSchema import invoiceSchema

class invoiceController():
    schemainv=invoiceSchema()
    schemasinv = invoiceSchema(many=True)

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

    def UPDATE_TOTAL(self,id,total,empresa):
        facturaUn=invoice.get_by_id(id)
        facturaUn.setTotal(total)
        facturaUn.setEmpresa(empresa)
        facturaUn.save()
        return self.schemainv.dump(facturaUn)

    def get_all_by_id(self,empresa):
        dat=invoice()
        produ=dat.simple_filter(empresa=empresa)
        return self.schemasinv.dump(produ)