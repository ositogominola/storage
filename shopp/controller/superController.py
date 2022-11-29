from .ventaController import ventaController
from .invoiceController import invoiceController


class superController:
    fctCon=invoiceController()
    prdCon=ventaController()

    def POST(self, data):
        prod=[]
        total=0
        factura = self.fctCon.POST()
        for produ in data["prd"]:
            produ["idfactura"] = factura["ventaProduc"]["id"]
            productos = self.prdCon.POST(produ)
            total+=productos["ventaProduc"]["precioTot"]
            prod.append(productos)
        factura=self.fctCon.UPDATE_TOTAL(factura["ventaProduc"]["id"],total)
        dicti = {"factura": factura, "productos": prod}
        return dicti
