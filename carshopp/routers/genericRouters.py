from flask import  Blueprint, request, make_response, json


generic=Blueprint("generic", __name__)


@generic.route('/setcookie', methods=['POST', 'GET'])
def setcookie():

    resp = make_response()
    for dat in request.get_json():
        print(dat["cantidad"],dat)
        resp.set_cookie(str(dat["cantidad"]),json.dumps(dat))
    return resp

@generic.route('/getcookie')
def getcookie():
   name = request.cookies.get()
   return '<h1>welcome ' + name + '</h1>'