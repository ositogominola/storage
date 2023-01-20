from models.Product import Producto
from schemas.Productschema import ProductSchema
from models.productInformation import infProduct
from .GeneralController import GeneralController
from settings.db import db
from schemas.InfProducSchema import InfProductoShema
class ControlerProduct(Producto):

    schemaPr = ProductSchema()
    schemaPrs = ProductSchema(many=True)
    General=GeneralController

    #comprueba que no exista un producto con el mismo nombre
    def comprobar_repeticion_empresa(self,data, idEmpresa):
        dat=self.General.obtener_relaciones(Producto,infProduct,idEmpresa)
        for row in dat:
            if row.name == data["name"]:
                return True
        return False

    #crea un producto sin su informacion
    def post(self, data, idEmpresa):
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

    #obtiene todos los articulos
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

    #obtiene por el id de la empresa
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

    #obtiene por un id
    def get_by_id(self, id, idEmpresa):
        dicti={}
        try:
            Prd = Producto.get_by_id(id=id)
            if Prd is None:
                producto = None
                successful = False
                message = "el producto bajo el id {} no existe".format(id)
            else:
                if idEmpresa["idEmpresa"]==Prd.prdinfo.idEmpresa:
                    successful = True
                    message = "productos cargados"
                    producto=self.schemaPr.dump(Prd)
                else:
                    successful = True
                    message = "la empresa no tiene el elemnto con id {}".format(idEmpresa["idEmpresa"])
                    producto = None
        except Exception as e:
            producto = None
            successful = False
            message = "ERROR: {}, TYPE: {}".format(e.args, type(e))
        dicti["Producto"] = producto
        dicti["successful"] = successful
        dicti["message"] = message
        return dicti

    #obtiene articulos por un dato especifico
    def get_by_data(self,empresa, **data):
        dicti={}
        try:
            name = data.get('name', '')
            if name != '':
                data.pop('name')
            search = "%{}%".format(name)
            Prd =Producto.query.filter(Producto.name.like(search)).filter_by(**data).join(infProduct).filter(infProduct.idEmpresa==empresa).all()
            if not Prd:
                producto = None
                successful = False
                message = "producto no encontrado"
            else:
                producto = self.schemaPrs.dump(Prd)
                successful = True
                message = "producto encontrado"
        except Exception as e:
            producto = None
            successful = False
            message = "ERROR: {}, TYPE: {}".format(e.args, type(e))
        dicti["Producto"] =producto
        dicti["successful"] = successful
        dicti["message"] =message
        return dicti

    #elimina el articulo con su informacion
    def delete(self, id, idEmpresa):
        dicti={}
        try:
            producto = Producto.get_by_id(id=id)
            if producto is None:
                successful = False
                message = "el producto bajo el id {} no existe".format(id)
            else:
                if idEmpresa["idEmpresa"] == producto.prdinfo.idEmpresa:
                    producto.delete()
                    successful = True
                    message = "el producto bajo el id {} fue eliminado correctamente".format(id)
                else:
                    successful = True
                    message = "la empresa no tiene el elemnto con id {}".format(idEmpresa["idEmpresa"])
        except Exception as e:
            successful = False
            message = "ERROR: {}, TYPE: {}".format(e.args, type(e))
        dicti["successful"] = successful
        dicti["message"] = message
        return dicti

    #actualiza el articulo y informacion exceptuando el id de empresa
    def update(self, id, **data):
        dicti={}
        try:
            producto = Producto.get_by_id(id=id)
            if producto is None:
                productoU = None
                successful = False
                message = "el producto bajo el id {} no existe".format(id)
            else:
                if producto.prdinfo.idEmpresa==data["infoPro"]["idEmpresa"]:
                    if not self.comprobar_repeticion_empresa(data["Product"],producto.prdinfo.idEmpresa):
                        for dt in data["Product"]:
                            if hasattr(producto, dt):
                                setattr(producto, dt, data["Product"][dt])
                        for dt in data["infoPro"]:
                            if hasattr(producto.prdinfo, dt) and dt!="idEmpresa": #no se puede cambiar el id de empresa
                                setattr(producto.prdinfo, dt,data["infoPro"][dt])
                        producto.save()
                        productoU = self.schemaPr.dump(producto)
                        successful = True
                        message = "el producto bajo el id {} fue actualizado".format(id)
                    else:
                        productoU = None
                        successful = True
                        message = "ya existe un producto con el nombre {}".format(data["Product"]["name"])
                else:
                    productoU = None
                    successful = False
                    message = "el producto bajo el id {} no existe".format(id)
        except Exception as e:
            productoU = None
            successful = False
            message = "ERROR: {}, TYPE: {}".format(e.args, type(e))
        dicti["Producto"] = productoU
        dicti["successful"] = successful
        dicti["message"] = message
        return dicti
