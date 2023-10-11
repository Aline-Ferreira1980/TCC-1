from flask import Blueprint

estagiario = Blueprint('estagiario', __name__)


@estagiario.route('/<id_usuario>')
def get_perfil(id_usuario):
    re