from datetime import datetime, timedelta
from typing import List

from flask import Blueprint, session, redirect, url_for, flash, request

from src.lib.auth import auth_required
from src.lib.utils import render
from src.model.agendamento import CreateAgendamento, Agendamento
from src.model.estagiario import HorarioTrabalhoRequest, Estagiario
from src.model.mapper import estagiario_mapper as estag_mapper, oauth_mapper, agendamento_mapper, paciente_mapper, \
    sala_mapper
from src.model.oauth_response import Token
from src.model.sala import Sala
from src.services import EstagiarioClient
from src.services.agendamento import AgendamentoClient
from src.services.paciente import PacienteClient
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
            'segunda': next((index for index, h in enumerate(estag.horariosTrabalho) if h.diaSemana == 'Segunda'), None),
            'terca': next((index for index, h in enumerate(estag.horariosTrabalho) if h.diaSemana == 'Terça'), None),
            'quarta': next((index for index, h in enumerate(estag.horariosTrabalho) if h.diaSemana == 'Quarta'), None),
            'quinta': next((index for index, h in enumerate(estag.horariosTrabalho) if h.diaSemana == 'Quinta'), None),
            'sexta': next((index for index, h in enumerate(estag.horariosTrabalho) if h.diaSemana == 'Sexta'), None),
            'sabado': next((index for index, h in enumerate(estag.horariosTrabalho) if h.diaSemana == 'Sabado'), None),
            'domingo': next((index for index, h in enumerate(estag.horariosTrabalho) if h.diaSemana == 'Domingo'), None)
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

        dias_semana = {'segunda': 'MONDAY',
                       'terca': 'TUESDAY',
                       'quarta': 'WEDNESDAY',
                       'quinta': 'THURSDAY',
                       'sexta': 'FRIDAY',
                       'sabado': 'SATURDAY',
                       'domingo': 'SUNDAY'}

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
    agendamento_cli = AgendamentoClient(token)

    resp = estag_client.get_by_id(id_usuario)
    if resp.valid:
        estag = estag_mapper.to_estagiario(resp.value)
        agendamentos = agendamento_cli.find_by_user_id(id_usuario)
        agenda: List[Agendamento] = agendamento_mapper.to_agendamentos(agendamentos.value)

        sorted_agenda = sorted(agenda, key= lambda a: a.inicioAgendamento, reverse=False) #Reverse = True para ordenar do mais no futuro para o presente

        data_atual = datetime.now().date()
        agendamentos_futuros = [agnd for agnd in sorted_agenda if
                                agnd.inicioAgendamento.date() >= data_atual]

        context = {
            'estagiario': estag,
            'agenda': agendamentos_futuros
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

    dia_semana = {'Segunda': 'Seg', 'Terça': 'Ter', 'Quarta': 'Qua', 'Quinta': 'Qui', 'Sexta': 'Sex', 'Sabado': 'Sab', 'Domingo': 'Dom'}

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
    duracao = int(request.form.get("duracao"))

    dia = int(data_str[0])
    mes = int(data_str[1])
    ano = int(data_str[2])
    hora = int(horario_str[0])
    minuto = int(horario_str[1])

    inicio_agendamento = datetime(ano, mes, dia, hora, minuto)
    fim_agendamento = datetime(ano, mes, dia, hora, minuto) + timedelta(minutes=duracao)

    agendamento: CreateAgendamento = CreateAgendamento(
        estagiarioEmail=email_estagiario,
        pacienteEmail=paciente,
        salaId=id_sala,
        inicioAgendamento=inicio_agendamento,
        fimAgendamento=fim_agendamento
    )

    agendamento_client = AgendamentoClient(token)
    result = agendamento_client.create_agendamento(agendamento)
    if result.valid:
        flash("agendamento realizado com sucesso", "success")
        return redirect(url_for('estagiario.get_agendamentos', id_usuario=user_token.payload.user_id))
    flash(result.message, "danger")
    return redirect(url_for('estagiario.get_novo_agendamento'))


@estagiario.route('/<id_usuario>/agendamentos',  methods=['POST'])
@auth_required
def post_agendamentos(id_usuario):
    token = session.get('token')
    user_token: Token = oauth_mapper.to_token(token)
    agendamento_cli = AgendamentoClient(token)

    if request.form.get('edit') == 'edit':
        agen_id = int(request.form.get('id_agendamento'))
        return redirect(url_for('estagiario.edit_agendamentos', id_agendamento=agen_id))

    if request.form.get('delete') == 'delete':
        agen_id = int(request.form.get('id_agendamento'))
        agendamento_cli.delete_agendandamento(agen_id)

    return redirect(url_for('estagiario.get_agendamentos', id_usuario=user_token.payload.user_id))


@estagiario.route('/agendamento/<id_agendamento>',  methods=['GET'])
@auth_required
def edit_agendamentos(id_agendamento):
    token = session.get('token')
    user_token: Token = oauth_mapper.to_token(token)
    agendamento_cli = AgendamentoClient(token)

    agenda_resp = agendamento_cli.find_by_id(id_agendamento)
    if agenda_resp.valid:
        agendamento = agendamento_mapper.to_agendamento(agenda_resp.value)

        estag_client = EstagiarioClient(token)
        sala_client = SalaClient(token)

        estag_resp = estag_client.get_by_id(user_token.payload.user_id)
        sala_resp = sala_client.list_salas()

        dia_semana = {'Segunda': 'Seg', 'Terça': 'Ter', 'Quarta': 'Qua', 'Quinta': 'Qui', 'Sexta': 'Sex',
                      'Sabado': 'Sab', 'Domingo': 'Dom'}

        if estag_resp.valid and sala_resp.valid:
            estag = estag_mapper.to_estagiario(estag_resp.value)
            salas = [Sala(**sala) for sala in sala_resp.value]
            dias_estagio = [{'dia_semana': dia_semana[dia_trab.diaSemana],
                             "inico": dia_trab.horarioInicio.strftime("%H:%M"),
                             "fim": dia_trab.horarioFim.strftime("%H:%M")} for dia_trab in estag.horariosTrabalho]

            context = {
                'estagiario': estag,
                'pacientes': estag.pacientes,
                'salas': salas,
                'dias_estagio': dias_estagio,
                'agendamento': agendamento
            }

            return render('estagiario/edit_agendamento.html', **context)

        flash("erro ao localizar o estagiario ou a sala do agendamento", "danger")
        return redirect(url_for('estagiario.get_agendamentos', id_usuario=user_token.payload.user_id))

    flash("agendamento nao encontrado", "danger")
    return redirect(url_for('estagiario.get_agendamentos', id_usuario=user_token.payload.user_id))


@estagiario.route('/agendamento/<id_agendamento>',  methods=['POST'])
@auth_required
def post_edit_agendamentos(id_agendamento):

    token = session.get('token')
    user_token: Token = oauth_mapper.to_token(token)

    if request.form.get('cancel') == 'cancel':
        return redirect(url_for('estagiario.get_agendamentos', id_usuario=user_token.payload.user_id))


    email_estagiario = user_token.payload.user_name
    paciente = request.form.get("paciente")
    id_sala = int(request.form.get("sala"))
    data_str = request.form.get("data_consulta").split("/")
    horario_str = request.form.get("horario_consulta").split(":")
    duracao = int(request.form.get("duracao"))

    dia = int(data_str[0])
    mes = int(data_str[1])
    ano = int(data_str[2])
    hora = int(horario_str[0])
    minuto = int(horario_str[1])

    inicio_agendamento = datetime(ano, mes, dia, hora, minuto)
    fim_agendamento = datetime(ano, mes, dia, hora, minuto) + timedelta(minutes=duracao)

    agendamento: CreateAgendamento = CreateAgendamento(
        estagiarioEmail=email_estagiario,
        pacienteEmail=paciente,
        salaId=id_sala,
        inicioAgendamento=inicio_agendamento,
        fimAgendamento=fim_agendamento
    )

    agendamento_client = AgendamentoClient(token)
    result = agendamento_client.update(id_agendamento, agendamento)
    if result.valid:
        flash("agendamento atualizado com sucesso", "success")
        return redirect(url_for('estagiario.get_agendamentos', id_usuario=user_token.payload.user_id))
    flash(result.message, "danger")
    return redirect(url_for('estagiario.get_novo_agendamento'))


@estagiario.route('/<id_usuario>/pacientes',  methods=['GET'])
@auth_required
def get_lista_pacientes(id_usuario):

    token = session.get('token')
    estag_client = EstagiarioClient(token)

    resp = estag_client.get_by_id(id_usuario)
    if resp.valid:
        estag: Estagiario = estag_mapper.to_estagiario(resp.value)

        context = {
            'estagiario': estag,
        }
        return render('estagiario/lista_pacientes.html', **context)

    flash(resp.message, "danger")
    return redirect(url_for('base.get_home'))


@estagiario.route('/paciente/<id_paciente>',  methods=['GET'])
@auth_required
def get_paciente_detalhe(id_paciente):

    token = session.get('token')
    estag_client = EstagiarioClient(token)
    user_token: Token = oauth_mapper.to_token(token)
    estag_resp = estag_client.get_by_id(user_token.payload.user_id)

    if estag_resp.valid:
        estag = estag_mapper.to_estagiario(estag_resp.value)
        paciente_cli = PacienteClient(token)
        paciente_resp = paciente_cli.get_by_id(id_paciente)
        paciente = paciente_mapper.to_paciente(paciente_resp.value)

        context = {
            'estagiario': estag,
            'paciente': paciente
        }
        return render('estagiario/detalhe_paciente.html', **context)


@estagiario.route('/pacientes_ofaos',  methods=['GET'])
@auth_required
def get_pacientes_orfao():

    token = session.get('token')
    paciente_client = PacienteClient(token)
    user_token: Token = oauth_mapper.to_token(token)

    pacientes_resp = paciente_client.get_by_estagiario_empty()
    if pacientes_resp.valid:
        estag_client = EstagiarioClient(token)
        estag_resp = estag_client.get_by_id(user_token.payload.user_id)
        estag = estag_mapper.to_estagiario(estag_resp.value)
        pacientes = paciente_mapper.to_pacientes(pacientes_resp.value)

        context = {
            'estagiario': estag,
            'pacientes': pacientes,
        }

        return render('estagiario/lista_pacientes_orfaos.html', **context)

    flash(pacientes_resp.message, "danger")
    return redirect(url_for('estagiario.get_agendamentos', id_usuario=user_token.payload.user_id))


@estagiario.route('/pacientes_ofaos',  methods=['POST'])
@auth_required
def post_pacientes_orfao():

    token = session.get('token')
    user_token: Token = oauth_mapper.to_token(token)

    id_paciente = request.form.get("btn_vincular")

    estag_client = EstagiarioClient(token)
    estag_resp = estag_client.add_paciente(user_token.payload.user_id, id_paciente)

    if estag_resp.valid:
        return redirect(url_for('estagiario.get_pacientes_orfao'))

    flash(estag_resp.message, "danger")
    return redirect(url_for('estagiario.get_agendamentos', id_usuario=user_token.payload.user_id))


@estagiario.route('/salas',  methods=['GET'])
@auth_required
def get_salas():

    token = session.get('token')
    user_token: Token = oauth_mapper.to_token(token)
    sala_client = SalaClient(token)

    salas_resp = sala_client.list_salas()
    if salas_resp.valid:
        salas = sala_mapper.to_salas(salas_resp.value)

        estag_client = EstagiarioClient(token)
        estag_resp = estag_client.get_by_id(user_token.payload.user_id)
        estag = estag_mapper.to_estagiario(estag_resp.value)

        context = {
            'estagiario': estag,
            'salas': salas
        }
        return render('estagiario/salas.html', **context)


    flash(salas_resp.message, "danger")
    return redirect(url_for('estagiario.get_agendamentos', id_usuario=user_token.payload.user_id))


@estagiario.route('/salas',  methods=['POST'])
@auth_required
def post_salas():

    token = session.get('token')
    sala_client = SalaClient(token)



    sala_id = request.form.get('delete_sala')
    if sala_id:
        resep_delete = sala_client.delete_sala(int(sala_id))

        if resep_delete.valid:
            return redirect(url_for('estagiario.get_salas'))

    else:
        sala = {'nome': request.form.get('nome_sala'), 'local': request.form.get('local_sala')}
        salas_resp = sala_client.cadastrar_sala(sala)
        if salas_resp.valid:
            return redirect(url_for('estagiario.get_salas'))

        flash(salas_resp.message, "danger")
        return redirect(url_for('estagiario.get_salas'))


@estagiario.route('sala/<id_sala>/agendamentos',  methods=['GET'])
@auth_required
def get_sala_agendamentos(id_sala):

    token = session.get('token')
    estag_client = EstagiarioClient(token)
    agendamento_cli = AgendamentoClient(token)
    user_token: Token = oauth_mapper.to_token(token)

    resp = estag_client.get_by_id(user_token.payload.user_id)
    if resp.valid:
        estag = estag_mapper.to_estagiario(resp.value)
        agendamentos = agendamento_cli.find_by_sala_id(id_sala)
        agenda: List[Agendamento] = agendamento_mapper.to_agendamentos(agendamentos.value)

        sala_cli = SalaClient(token)
        sala_resp = sala_cli.get_by_id(id_sala)
        sala = sala_mapper.to_sala(sala_resp.value)

        sorted_agenda = sorted(agenda, key= lambda a: a.inicioAgendamento, reverse=False) #Reverse = True para ordenar do mais no futuro para o presente

        data_atual = datetime.now().date()
        agendamentos_futuros = [agnd for agnd in sorted_agenda if
                                agnd.inicioAgendamento.date() >= data_atual]

        context = {
            'estagiario': estag,
            'agenda': agendamentos_futuros,
            'sala': sala
        }
        return render('estagiario/agendamentos_sala.html', **context)





