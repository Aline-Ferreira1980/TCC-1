from urllib.parse import urlencode

from src.model.estagiario import Estagiario, AtualizaEstagiarioRequest, CadastraEstagiarioRequest
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

    def add_paciente(self, id_estagiario, id_paciente):
        data = {'id_paciente': id_paciente}

        data = urlencode(data)
        headers = {'Content-Type': 'application/x-www-form-urlencoded'}
        return self.post(f"estagiario/{id_estagiario}/add_paciente", params=data, headers=headers)

    def create_estagiario(self, estag: CadastraEstagiarioRequest):
        return self.post("estagiario/cadastrar", data=estag.model_dump())
