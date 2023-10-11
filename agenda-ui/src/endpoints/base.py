from flask import Blueprint, redirect, url_for, flash, session

from src.lib.utils import render, read_form_field
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

    session['user'] = response.value.get('user_nome')
    session['token'] = response.value.get('access_token')
    session['refresh_token'] = response.value.get('refresh_token')
    flash(response.messages, 'success')

