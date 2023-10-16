import logging
from datetime import datetime
from typing import Any

from flask import session, render_template, request
from markupsafe import escape


def render(name: str, **variables):
    '''
    Função genérica de render que adiciona valores extras
    nas variáveis de contexto do template, como o status do login
    '''
    context = {
        **variables,
        'remaining': session.get('remaining'),
        'is_logged': 'logged' if get_login_status() else None,
        'nome_usuario': get_username() if get_login_status() else None
    }
    return render_template(name, **context)


def get_login_status():
    if session.get('token') and session.get('login_expiration'):
        login_expiration = session.get('login_expiration')
        try:
            remaining = login_expiration - datetime.now().timestamp()
            # remaining time in seconds
            session['remaining'] = remaining
            # True if still logged
            return remaining > 0
        except ValueError as e:
            session['token'] = None
            logging.error(str(e))
            return False
    return False


def get_username():
    return session.get('user')


def read_form_field(field: str, default: Any = None) -> Any:
    value = request.form.get(field, default)
    if value is None:
        return value
    return str(escape(value))
