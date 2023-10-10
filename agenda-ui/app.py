from flask import Flask

from src.endpoints.base import base

app = Flask(__name__)
app.register_blueprint(base)

#
# @app.route('/')
# def hello_world():  # put application's code here
#     return 'Hello World!'


if __name__ == '__main__':
    app.run()
