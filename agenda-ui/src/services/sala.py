from src.services.agenda import AgendaClient


class SalaClient(AgendaClient):
    def __init__(self, auth_token):
        super().__init__()
        self.update_headers("Authorization", "Bearer " + auth_token)

    def list_salas(self):
        return self.get(f"sala/listar")

    def get_by_id(self, id: int):
        return self.get(f"/sala/{id}")

    def cadastrar_sala(self, sala):
        return self.post(f"/sala/cadastrar", data=sala)

    def delete_sala(self, id: int):
        return self.delete(f"/sala/{id}")
