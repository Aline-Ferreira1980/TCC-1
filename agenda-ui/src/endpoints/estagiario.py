from flask import Blueprint, session, redirect, url_for, flash, request

from src.lib.utils import render
from src.model.estagiario import Estagiario
from src.model.mapper import estagiario_mapper as estag_mapper
from src.services import EstagiarioClient

estagiario = Blueprint('estagiario', __name__)


@estagiario.route('/<id_usuario>',  methods=['GET'])
def get_perfil(id_usuario):
    token = session.get('token')
    estag_client = EstagiarioClient(token)

    resp = estag_client.get_by_id(id_usuario)
    if resp.valid:
        estag = estag_mapper.to_estagiario(resp.value)
        context = {
            'estagiario': estag,
            'segunda': next((index for index, h in enumerate(estag.horariosTrabalho) if h.diaSemana == 0), None),
            'terca': next((index for index, h in enumerate(estag.horariosTrabalho) if h.diaSemana == 1), None),
            'quarta': next((index for index, h in enumerate(estag.horariosTrabalho) if h.diaSemana == 2), None),
            'quinta': next((index for index, h in enumerate(estag.horariosTrabalho) if h.diaSemana == 3), None),
            'sexta': next((index for index, h in enumerate(estag.horariosTrabalho) if h.diaSemana == 4), None),
            'sabado': next((index for index, h in enumerate(estag.horariosTrabalho) if h.diaSemana == 5), None),
            'domingo': next((index for index, h in enumerate(estag.horariosTrabalho) if h.diaSemana == 6), None)
        }
        return render('estagiario/perfil.html', **context)

    flash(resp.message, "error")
    return redirect(url_for('base.get_home'))


@estagiario.route('/<id_usuario>',  methods=['POST'])
def post_perfil(id_usuario):
    token = session.get('token')
    estag_client = EstagiarioClient(token)

    resp = estag_client.get_by_id(id_usuario)
    if resp.valid:
        estag = estag_mapper.to_estagiario(resp.value)
        estag.nome = request.form.get("nome")
        estag.sobrenome = request.form.get("sobrenome")

        estag_client.update_estagiario(id_usuario, estag)
