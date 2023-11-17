from flask import Flask

from src.config import ENV
from src.endpoints.base import base
from src.endpoints.estagiario import estagiario
from src.endpoints.paciente import paciente

app = Flask(__name__)
app.jinja_env.globals['VERSION'] = open('VERSION').read()
app.secret_key = ENV.APP_SECRET_KEY
app.url_map.strict_slashes = False

app.register_blueprint(base)
app.register_blueprint(estagiario, url_prefix='/estagiario')
app.register_blueprint(paciente, url_prefix='/paciente')


if __name__ == '__main__':
    app.run()
