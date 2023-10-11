from flask import Blueprint, redirect, url_for, flash, session

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
        flash(response.message, "error")
        return redirect(url_for('base.get_login'))

    oauth: OAuthResponseModel = oauth_mapper.to_oauth_response(response.value)

    session['user'] = oauth.access_token.payload.user_nome
    session['token'] = oauth.access_token.value
    session['refresh_token'] = oauth.refresh_token.value

    # token: Token = session['token']

    flash(response.message, 'success')

