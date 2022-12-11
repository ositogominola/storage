from flask import json

def loadFileConfig():
    with open('settings/config.json') as f:
        data = json.load(f)
    return data