from models.Product import Producto
from schemas.Productschema import ProductSchema
from models.productInformation import infProduct
from .GeneralController import GeneralController
from schemas.InfProducSchema import InfProductoShema
class ControlerProduct(Producto):

    schemaPr = ProductSchema()
    schemaPrs = ProductSchema(many=True)
    General=GeneralController

    def comprobar_repeticion_empresa(self,data, idEmpresa):
        dat=self.General.obtener_relaciones(Producto,infProduct,idEmpresa)
        for row in dat:
            if row.name == data["name"]:
                return True
        return False

    def post(self, data,idEmpresa,inf):
        dicti = {}
        try:
            product_schema = self.schemaPr.load(data)
            if not self.comprobar_repeticion_empresa(data, idEmpresa):
                productoUN = Producto(**product_schema)
                productoUN.save()
                ProductoUni = productoUN.id
                successful=True
                message="el registro fue exitoso"
            else:
                ProductoUni = None
                successful = False
                message = "el producto bajo el nombre {} ya existe".format(product_schema["name"])
        except Exception as e:
            ProductoUni = None
            successful = False
            message = "ERROR {} TYPE {}".format(e.args, type(e))
        dicti["Producto"] = ProductoUni
        dicti["successful"] = successful
        dicti["message"] = message
        return dicti

    def get_all(self):
        dicti={}
        try:
            producto = Producto.get_all()
            if producto is None:
                producto = None
                successful=False
                message = "no hay productos registrados"
            producto = self.schemaPrs.dump(producto)
            successful = True
            message = "productos cargados"
        except Exception as e:
            producto = None
            successful = False
            message = "ERROR {} TYPE {}".format(e.args, type(e))
        dicti["Producto"] = producto
        dicti["successful"] = successful
        dicti["message"] = message
        return dicti

    def get_by_company(self,idEmpresa):
        dicti={}
        try:
            PRDS= self.schemaPrs.dump(self.General.obtener_relaciones(Producto, infProduct, idEmpresa))
            if PRDS==[]:
                producto=None
                successful = False
                message = "no hay productos registrados"
            else:
                successful = True
                message = "productos cargados"
                producto=PRDS
        except Exception as e:
            producto= None
            successful = False
            message = "ERROR: {}, TYPE: {}".format(e.args, type(e))
        dicti["Producto"]=producto
        dicti["successful"] = successful
        dicti["message"] = message
        return dicti

    def get_by_id(self, id):
        dicti={}
        try:
            Prd = Producto.get_by_id(id=id)
            if Prd is None:
                producto = None
                successful = False
                message = "el producto bajo el id {} no existe".format(id)
            else:
                successful = True
                message = "productos cargados"
                producto=self.schemaPr.dump(Prd)
        except Exception as e:
            producto = None
            successful = False
            message = "ERROR: {}, TYPE: {}".format(e.args, type(e))
        dicti["Producto"] = producto
        dicti["successful"] = successful
        dicti["message"] = message
        return dicti

    def get_by_data(self, **data):
        dicti={}
        try:
            Prd = Producto.simple_filter(**data)
            if Prd is None:
                producto = None
                successful = False
                message = "producto no encontrado"
            producto = self.schemaPrs.dump(Prd)
            successful = True
            message = "producto encontrado"
        except Exception as e:
            producto = None
            successful = False
            message = "ERROR: {}, TYPE: {}".format(e.args, type(e))
        dicti["Producto"] = producto
        dicti["successful"] = successful
        dicti["message"] = message
        return dicti

    def delete(self, id):
        dicti={}
        try:
            producto = Producto.get_by_id(id=id)
            if producto is None:
                successful = False
                message = "el producto bajo el id {} no existe".format(id)
            producto.delete()
            successful = True
            message = "el producto bajo el id {} fue eliminado correctamente".format(id)
        except Exception as e:
            successful = False
            message = "ERROR: {}, TYPE: {}".format(e.args, type(e))
        dicti["successful"] = successful
        dicti["message"] = message
        return dicti

    def update(self, id, **data):
        dicti={}
        try:
            producto = Producto.get_by_id(id=id)
            if producto is None:
                productoU = None
                successful = False
                message = "el producto bajo el id {} no existe".format(id)
            for dt in data["Product"]:
                if hasattr(producto, dt):
                    setattr(producto, dt, data["Product"][dt])
            for dt in data["infoPro"]:
                if hasattr(producto.prdinfo, dt) and dt!="idEmpresa":
                    setattr(producto.prdinfo, dt,data["infoPro"][dt])
            producto.save()
            productoU = self.schemaPr.dump(producto)
            successful = False
            message = "el producto bajo el id {} fue actualizado".format(id)
        except Exception as e:
            productoU = None
            successful = False
            message = "ERROR: {}, TYPE: {}".format(e.args, type(e))
        dicti["Producto"] = productoU
        dicti["successful"] = successful
        dicti["message"] = message
        return dicti
