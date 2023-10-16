from src.model.estagiario import Estagiario, AtualizaEstagiarioRequest
from src.services.agenda import AgendaClient


class EstagiarioClient(AgendaClient):
    def __init__(self, auth_token):
        super().__init__()
        self.base_url = self.base_url
        self.update_headers("Authorization", "Bearer " + auth_token)

    def get_by_id(self, id):
        return self.get(f"estagiario/{id}")

    def update_estagiario(self, id_usuario, estagiario: AtualizaEstagiarioRequest):
        return self.put(f"estagiario/{id_usuario}", data=estagiario.model_dump())


