from models.productInformation import infProduct as infPoduct
from schemas.InfProducSchema import InfProductoShema as SchrmaInfo

class InfProdController:

    schemaInfoProd= SchrmaInfo()
    schemaInfoProds = SchrmaInfo(many=True)

    def post(self, data):
        dicti={}
        try:
            #info_schema=self.schemaInfoProd(data)

            if not self.get_by_data(idProducto=data["idProducto"]):
                infPoductu=infPoduct(**data)
                infPoductu.datatime()
                infPoductu.save()
                IDprodInf=infPoductu.id
                successful = True
                message = "la informacion fue refistrada con exitos"

            else:
                IDprodInf=None
                successful = False
                message = "el producto bajo el id {} ya existe".format(data["idProducto"])
        except Exception as e:
            IDprodInf = None
            successful = False
            message ="ERROR {} TYPE {}".format(e.args, type(e))
        dicti["IDprodInf"]=IDprodInf
        dicti["successful"] = successful
        dicti["message"] = message
        return dicti

    def get_all(self):
        pass

    def get_by_id(self):
        pass

    def get_by_data(self, **data):
        try:
            producto = infPoduct.simple_filter(**data)
            if producto is None:
                return "el producto no fue encontrado"
        except Exception as e:
            return e.args
        return self.schemaInfoProds.dump(producto)

    def delete(self):
        pass

    def update(self):
        pass

