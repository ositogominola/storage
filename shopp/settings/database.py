from flask_sqlalchemy import SQLAlchemy

db=SQLAlchemy()

def databasesetting(app):
    app.config['SQLALCHEMY_DATABASE_URI'] = 'mysql+pymysql://root:1000579643@127.0.0.1:3306/ventas'
    app.config["SQLALCHEMY_TRACK_MODIFICATIONS"] = False

class BaseModel:
    @classmethod
    def get_Foreign(cls,model,subq,com):
        return db.session.query(model).join(subq).filter(subq.idEmpresa==com).all()

    def save(self):
        db.session.add(self)
        db.session.commit()

    def delete(self):
        db.session.delete(self)
        db.session.commit()

    @classmethod
    def get_all(cls):
        return cls.query.all()

    @classmethod
    def get_by_id(cls, id):
        return cls.query.get(id)

    @classmethod
    def simple_filter(cls, **kwargs):
        return cls.query.filter_by(**kwargs).all()