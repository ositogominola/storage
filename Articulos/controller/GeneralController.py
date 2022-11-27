from settings.db import BaseModelMixin

class GeneralController():
    mymodel=BaseModelMixin

    @classmethod
    def obtener_relaciones(cls,modelo, modelorelacional, filtro):
        return cls.mymodel.get_Foreign(model=modelo,subq=modelorelacional,com=filtro)


