from flask import Blueprint, session, flash, redirect, url_for

from src.lib.auth import auth_required
from src.lib.utils import render
from src.model.mapper import paciente_mapper
from src.services.paciente import PacienteClient

paciente = Blueprint('paciente', __name__)


@paciente.route('/<id_usuario>',  methods=['GET'])
@auth_required
def get_perfil(id_usuario):
    token = session.get('token')
    paciente_cliente = PacienteClient(token)

    paci_resp = paciente_cliente.get_by_id(id_usuario)

    if paci_resp.valid:
        paciente = paciente_mapper.to_paciente(paci_resp.value)

        context = {
            'paciente': paciente
        }

        return render('pacientes/perfil.html', **context)

    flash(paci_resp.message, "error")
    return redirect(url_for('base.get_home'))