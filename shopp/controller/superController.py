from .ventaController import ventaController
from .invoiceController import invoiceController


class superController:
    fctCon=invoiceController()
    prdCon=ventaController()

    #crea una factura y le añade los productos
    def POST(self, data,idEmpresa):
        total=0
        try:
            factura = self.fctCon.POST()
            print(data["prd"])
            for produ in data["prd"]:
                if idEmpresa==produ["idempresa"]:
                    produ["idfactura"] = factura["ventaProduc"]["id"]
                    productos = self.prdCon.POST(produ)
                    print(productos)
                    total+=productos["ventaProduc"]["precioTot"]
            factura=self.fctCon.UPDATE_TOTAL(factura["ventaProduc"]["id"],total,idEmpresa)
        except Exception as e:
            factura={"message": "error: "+e, "successful": False}
        return factura
