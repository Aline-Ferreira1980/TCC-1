from src.services.agenda import AgendaClient


class SalaClient(AgendaClient):
    def __init__(self, auth_token):
        super().__init__()
        self.update_headers("Authorization", "Bearer " + auth_token)

    def list_salas(self):
        return self.get(f"sala/listar")
