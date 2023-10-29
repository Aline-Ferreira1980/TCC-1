from datetime import datetime
from typing import List

from flask import Blueprint, session, flash, redirect, url_for, request

from src.lib.auth import auth_required
from src.lib.utils import render
from src.model.agendamento import Agendamento
from src.model.mapper import paciente_mapper, agendamento_mapper
from src.model.paciente import Telefone, AtualizaPacienteRequest, CadastraPacienteRequest, Endereco
from src.services.agendamento import AgendamentoClient
from src.services.paciente import PacienteClient

paciente = Blueprint('paciente', __name__)


@paciente.route('/<id_usuario>', methods=['GET'])
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


@paciente.route('/<id_usuario>', methods=['POST'])
@auth_required
def post_perfil(id_usuario):
    token = session.get('token')
    paciente_client = PacienteClient(token)

    resp = paciente_client.get_by_id(id_usuario)
    if resp.valid:
        paciente = paciente_mapper.to_atualiza_request(resp.value)
        form = request.form

        if form.get('deletaPaciente'):
            paciente_client.deleta_paciente(id_usuario)
            flash("Perfil deletado", "warn")
            return redirect(url_for('base.get_home'))

        paciente.nome = form.get('nome')
        paciente.sobrenome = form.get('sobrenome')

        data_nascimento_str = form.get('dataNascimento')
        formato = '%d/%m/%Y'
        paciente.dataNascimento = datetime.strptime(data_nascimento_str, formato)

        paciente.nomeSocial = form.get('nomeSocial')
        paciente.genero = form.get('genero')
        paciente.etniaRacial = form.get('etniaRacial')
        paciente.estadoCivil = form.get('estadoCivil')

        paciente.endereco.rua = form.get('endereco_rua')
        paciente.endereco.cidade = form.get('endereco_cidade')
        paciente.endereco.bairro = form.get('endereco_bairro')
        paciente.endereco.cep = form.get('endereco_cep')

        # popula a informação de telefone do paciente
        paciente.telefone.clear()
        for chave, valor in form.items():
            if chave.startswith('telefone_telefone'):
                indice = chave.split('telefone_telefone')[-1]
                if valor.strip():  # Verifique se o valor do telefone não está em branco
                    if indice == 'extra':
                        chave_telefone = 'extra'
                    else:
                        chave_telefone = indice
                    tipo_chave = f'telefone_tipo{chave_telefone}'

                    tmp_tel = Telefone(telefone=valor, tipo=form[tipo_chave])
                    paciente.telefone.append(tmp_tel)

        if form.get('isMenorIdade'):
            paciente.isMenorIdade = True
            paciente.representanteNome = form.get('representanteNome')
            paciente.relacaoRepresentante = form.get('relacaoRepresentante')
        else:
            paciente.isMenorIdade = False
            paciente.representanteNome = None
            paciente.relacaoRepresentante = None

        result = paciente_client.update_paciente(id_usuario, paciente)
        if not result.valid:
            flash(result.message, "danger")
            return redirect(url_for('paciente.get_perfil', id_usuario=id_usuario))

        flash("Perfil salvo", "success")
        return redirect(url_for('paciente.get_agendamentos', id_usuario=id_usuario))


@paciente.route('/<id_usuario>/agendamentos', methods=['GET'])
@auth_required
def get_agendamentos(id_usuario):
    token = session.get('token')
    paci_client = PacienteClient(token)
    agendamento_cli = AgendamentoClient(token)

    resp = paci_client.get_by_id(id_usuario)
    if resp.valid:
        paciente = paciente_mapper.to_paciente(resp.value)
        agendamentos = agendamento_cli.find_by_user_id(id_usuario)
        agenda: List[Agendamento] = agendamento_mapper.to_agendamentos(agendamentos.value)

        sorted_agenda = sorted(agenda, key=lambda a: a.inicioAgendamento,
                               reverse=False)  # Reverse = True para ordenar do mais no futuro para o presente

        data_atual = datetime.now().date()
        agendamentos_futuros = [agnd for agnd in sorted_agenda if
                                agnd.inicioAgendamento.date() >= data_atual]

        context = {
            'paciente': paciente,
            'agenda': agendamentos_futuros
        }
        return render('pacientes/agendamentos.html', **context)


def cadastro_paciente(form):

    data_nascimento_str = form.get('dataNascimento')
    formato = '%d/%m/%Y'

    paciente = CadastraPacienteRequest(
        nome=form.get('nome'),
        sobrenome=form.get('sobrenome'),
        email=form.get('email'),
        senha=form.get('senha'),
        nomeSocial=form.get('nomeSocial'),
        dataNascimento=datetime.strptime(data_nascimento_str, formato),
        genero=form.get('genero'),
        estadoCivil=form.get('estadoCivil'),
        etniaRacial=form.get('etniaRacial'),
        endereco=Endereco(
            rua=form.get('endereco_rua'),
            cidade=form.get('endereco_cidade'),
            bairro=form.get('endereco_bairro'),
            cep=form.get('endereco_cep')
        )
    )

    paciente.telefone.clear()
    for chave, valor in form.items():
        if chave.startswith('telefone_telefone'):
            indice = chave.split('telefone_telefone')[-1]
            if valor.strip():  # Verifique se o valor do telefone não está em branco
                if indice == 'extra':
                    chave_telefone = 'extra'
                else:
                    chave_telefone = indice
                tipo_chave = f'telefone_tipo{chave_telefone}'

                tmp_tel = Telefone(telefone=valor, tipo=form[tipo_chave])
                paciente.telefone.append(tmp_tel)

    if form.get('isMenorIdade'):
        paciente.isMenorIdade = True
        paciente.representanteNome = form.get('representanteNome')
        paciente.relacaoRepresentante = form.get('relacaoRepresentante')
    else:
        paciente.isMenorIdade = False
        paciente.representanteNome = None
        paciente.relacaoRepresentante = None

    paciente_client = PacienteClient('')
    resp = paciente_client.create_paciente(paciente)
    if resp.valid:
        return True
    return False

