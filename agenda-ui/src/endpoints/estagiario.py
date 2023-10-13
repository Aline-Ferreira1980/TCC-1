from flask import Blueprint, session

from src.services import EstagiarioClient

estagiario = Blueprint('estagiario', __name__)

token = session.get('token')
estag_client = EstagiarioClient(token)

@estagiario.route('/<id_usuario>')
def get_perfil(id_usuario):
    resp = estag_client.get_by_id(id_usuario)
    print(resp)


