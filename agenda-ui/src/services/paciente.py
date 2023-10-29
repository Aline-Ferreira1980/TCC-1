from src.model.paciente import AtualizaPacienteRequest, CadastraPacienteRequest
from src.services.agenda import AgendaClient


class PacienteClient(AgendaClient):
    def __init__(self, auth_token):
        super().__init__()
        self.base_url = self.base_url
        self.update_headers("Authorization", "Bearer " + auth_token)

    def get_by_id(self, id):
        return self.get(f"paciente/{id}")

    def get_by_estagiario_empty(self):
        return self.get("/paciente/findByEstagiarioEmpty")

    def update_paciente(self, id_usuario, paciente: AtualizaPacienteRequest):
        return self.put(f"paciente/{id_usuario}", data=paciente.model_dump())

    def deleta_paciente(self, id_usuario):
        return self.delete(f"/paciente/{id_usuario}")

    def create_paciente(self, paciente: CadastraPacienteRequest):
        return self.post("paciente/cadastrar", data=paciente.model_dump())

