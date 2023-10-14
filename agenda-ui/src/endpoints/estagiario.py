from flask import Blueprint, session

from src.lib.utils import render
from src.services import EstagiarioClient

estagiario = Blueprint('estagiario', __name__)


@estagiario.route('/<id_usuario>',  methods=['GET'])
def get_perfil(id_usuario):
    token = session.get('token')
    estag_client = EstagiarioClient(token)

    resp = estag_client.get_by_id(id_usuario)

    return render('estagiario/perfil.html')


