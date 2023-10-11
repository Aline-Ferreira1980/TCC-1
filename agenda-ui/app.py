from flask import Flask

from src.config import ENV
from src.endpoints.base import base


app = Flask(__name__)
app.jinja_env.globals['VERSION'] = open('VERSION').read()
app.secret_key = ENV.APP_SECRET_KEY
app.url_map.strict_slashes = False

app.register_blueprint(base)


if __name__ == '__main__':
    app.run()
