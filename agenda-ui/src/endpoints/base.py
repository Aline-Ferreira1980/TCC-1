from flask import Blueprint, redirect, url_for

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
    print(response)


