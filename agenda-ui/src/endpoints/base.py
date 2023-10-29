from datetime import datetime

from flask import Blueprint, redirect, url_for, flash, session, request

from src.endpoints import estagiario, paciente
from src.lib.utils import render, read_form_field
from src.model.oauth_response import OAuthResponseModel
from src.model.mapper import oauth_mapper
from src.services import auth

base = Blueprint('base', __name__)


@base.route('/', methods=['GET'])
def get_home():
    return redirect(url_for('base.get_login'))


@base.route('/login', methods=['GET'])
def get_login():
    return render('login.html')


@base.route('/login', methods=['POST'])
def post_login():
    username = read_form_field('username').strip()
    password = read_form_field('password').strip()

    response = auth.login(username, password)
    if not response.valid:
        # TODO melhorar msg de erro ao logar
        flash(response.message, "error")
        return redirect(url_for('base.get_login'))

    oauth: OAuthResponseModel = oauth_mapper.to_oauth_response(response.value)

    session['user'] = oauth.access_token.payload.user_nome
    session['token'] = oauth.access_token.value
    session['refresh_token'] = oauth.refresh_token.value
    session['login_expiration'] = datetime.now().timestamp() + oauth.expires_in
    session['authorities'] = oauth.access_token.payload.authorities

    if 'paciente' in session['authorities']:
        return redirect(url_for("paciente.get_perfil", id_usuario=oauth.user_id))
    if 'estagiario' in  session['authorities']:
        return redirect(url_for("estagiario.get_perfil", id_usuario=oauth.user_id))

    flash("Nao existe rota para o 'Authority' informado no token", 'danger')
    return redirect(url_for('base.get_home'))

@base.route('/logout', methods=['GET'])
def get_logout():
    session['login_expiration'] = None
    session['token'] = None
    return redirect(url_for('base.get_home'))


@base.route('/cadastro', methods=['GET'])
def get_paciente_cadastro():
    return render('cadastro.html')



@base.route('/cadastro', methods=['POST'])
def post_paciente_cadastro():
    form = request.form
    if paciente.cadastro_paciente(form):
        return redirect(url_for('base.get_confirmar_email'))

    flash("Erro ao realziar o cadastro, tente novamente mais tarde", "danger")
    return redirect(url_for('base.get_login'))


@base.route('/cadastro/estagiario', methods=['GET'])
def get_estagiario_cadastro():
    return render('cadastro_estagiario.html')


@base.route('/cadastro/estagiario', methods=['POST'])
def post_estagiario_cadastro():
    form = request.form
    if estagiario.cadastro_estagiario(form):
        return redirect(url_for('base.get_confirmar_email'))

    flash("Erro ao realziar o cadastro, tente novamente mais tarde", "danger")
    return redirect(url_for('base.get_login'))


@base.route('/cadastro/confirmar', methods=['GET'])
def get_confirmar_email():
    return render('cadastro_realizado.html')