from datetime import datetime
from typing import List

from flask import Blueprint, session, redirect, url_for, flash, request

from src.lib.auth import auth_required
from src.lib.utils import render
from src.model.agendamento import CreateAgendamento
from src.model.estagiario import HorarioTrabalhoRequest
from src.model.mapper import estagiario_mapper as estag_mapper, oauth_mapper
from src.model.oauth_response import Token
from src.model.sala import Sala
from src.services import EstagiarioClient
from src.services.agendamento import AgendamentoClient
from src.services.sala import SalaClient

estagiario = Blueprint('estagiario', __name__)


@estagiario.route('/<id_usuario>',  methods=['GET'])
@auth_required
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
@auth_required
def post_perfil(id_usuario):
    token = session.get('token')
    estag_client = EstagiarioClient(token)

    resp = estag_client.get_by_id(id_usuario)
    if resp.valid:
        estag = estag_mapper.to_atualiza_request(resp.value)
        estag.nome = request.form.get("nome")
        estag.sobrenome = request.form.get("sobrenome")
        estag.ra = request.form.get("ra_uscs").replace('-', '')
        estag.turno = request.form.get("turno").upper()
        estag.turma = request.form.get("turma")
        estag.semestre = int(request.form.get("semestre"))
        estag.horariosTrabalho.clear()

        dias_semana = {'segunda': 0,
                       'terca':1,
                       'quarta':2,
                       'quinta':3,
                       'sexta':4,
                       'sabado':5,
                       'domingo':6}

        horarios_trabalho: List[HorarioTrabalhoRequest] = []
        temp_dict = {}
        for key, value in request.form.items():
            if key.startswith('cb_'):
                dia_semana = key[3:]
                temp_dict['diaSemana'] = dias_semana[dia_semana]
                temp_dict['horarioInicio'] = request.form.get(f"{dia_semana}_inicio")
                temp_dict['horarioFim'] = request.form.get(f"{dia_semana}_fim")
                horario_trabalho = HorarioTrabalhoRequest(** temp_dict.copy())
                horarios_trabalho.append(horario_trabalho)

        estag.horariosTrabalho = horarios_trabalho.copy()

        result = estag_client.update_estagiario(id_usuario, estag)
        if not result.valid:
            flash(result.message, "danger")
            return redirect(url_for('estagiario.get_perfil', id_usuario=id_usuario))

        flash("estagiario salvo", "success")
        return redirect(url_for('estagiario.get_perfil', id_usuario=id_usuario))


@estagiario.route('/<id_usuario>/agendamentos',  methods=['GET'])
@auth_required
def get_agendamentos(id_usuario):
    token = session.get('token')
    estag_client = EstagiarioClient(token)
    resp = estag_client.get_by_id(id_usuario)
    if resp.valid:
        estag = estag_mapper.to_estagiario(resp.value)
        context = {
            'estagiario': estag
        }
        return render('estagiario/agendamentos.html', **context)


@estagiario.route('agendamento/novo',  methods=['GET'])
@auth_required
def get_novo_agendamento():
    token = session.get('token')
    user_token: Token = oauth_mapper.to_token(token)
    estag_client = EstagiarioClient(token)
    sala_client = SalaClient(token)
    resp = estag_client.get_by_id(user_token.payload.user_id)
    sala_resp = sala_client.list_salas()

    dia_semana = {0: 'Seg', 1: 'Ter', 2: 'Qua', 3: 'Qui', 4: 'Sex', 5: 'Sab', 6: 'Dom'}

    if resp.valid and sala_resp.valid:

        estag = estag_mapper.to_estagiario(resp.value)
        salas = [Sala(**sala) for sala in sala_resp.value]
        dias_estagio = [{'dia_semana': dia_semana[dia_trab.diaSemana],
                         "inico": dia_trab.horarioInicio.strftime("%H:%M"),
                         "fim": dia_trab.horarioFim.strftime("%H:%M") } for dia_trab in estag.horariosTrabalho]

        context = {
            'estagiario': estag,
            'pacientes': estag.pacientes,
            'salas': salas,
            'dias_estagio': dias_estagio
        }
        return render('estagiario/agendamento.html', **context)
    else:
        flash(resp.message + sala_resp.message, "danger")


@estagiario.route('agendamento/novo',  methods=['POST'])
@auth_required
def post_novo_agendamento():
    token = session.get('token')
    user_token: Token = oauth_mapper.to_token(token)
    email_estagiario = user_token.payload.user_name
    paciente = request.form.get("paciente")
    id_sala = int(request.form.get("sala"))
    data_str = request.form.get("data_consulta").split("/")
    horario_str = request.form.get("horario_consulta").split(":")
    duracao_str = int(request.form.get("duracao"))

    dia = int(data_str[0])
    mes = int(data_str[1])
    ano = int(data_str[2])
    hora = int(horario_str[0])
    minuto = int(horario_str[1])

    inicio_agendamento = datetime(ano, mes, dia, hora, minuto)
    fim_agendamento = datetime(ano, mes, dia, hora, minuto + duracao_str)

    agendamento: CreateAgendamento = CreateAgendamento(
        estagiarioEmail=email_estagiario,
        pacienteEmail=paciente,
        salaId=id_sala,
        inicioAgendamento=inicio_agendamento,
        fimAgendamento=fim_agendamento
    )
    dump = agendamento.model_dump()
    agendamento_client = AgendamentoClient(token)
    result = agendamento_client.create_agendamento(agendamento)
    if result.valid:
        flash("agendamento realizado com sucesso", "success")
        return redirect(url_for('estagiario.get_agendamentos'))
    flash(result.message, "danger")
    return redirect(url_for('estagiario.get_novo_agendamento'))
