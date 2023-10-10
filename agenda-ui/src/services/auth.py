import base64
from urllib.parse import urlencode

from src.config import ENV
from src.services.agenda import AgendaClient


class Auth(AgendaClient):

    def __init__(self):
        super().__init__()

    def login(self, username, password):
        data = {
            "grant_type": "password",
            "username": f"{username}",
            "password": f"{password}"
        }
        data = urlencode(data)
        logn_headers = {
            'Authorization': 'Basic ' + base64.b64encode(
                f"{ENV.AGENDA_CLIENT.CLIENT_ID}:{ENV.AGENDA_CLIENT.CLIENT_SECRET}".encode()).decode(),
            'Content-Type': 'application/x-www-form-urlencoded'
        }
        r = self.post("oauth/token", params=data, headers=logn_headers)
        print (r.value)
        return r