import functools

from flask import session, flash, redirect, url_for

from src.lib.utils import get_login_status


def auth_required(method):
    @functools.wraps(method)
    def decorator(*args, **kwargs):
        if not get_login_status():
            session['token'] = None
            message = '''
                O token de autenticação está inválido ou expirou.
                É necessário permissão para solicitar e ler
                os segredos dos certificados. Solicite acesso ao grupo
                "autocert" no IDM e autentique-se com seu login de rede.
            '''
            flash(message, 'error')
            return redirect(url_for('base.get_login'))
        return method(*args, **kwargs)
    return decorator