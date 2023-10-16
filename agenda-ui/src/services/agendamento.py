from src.model.agendamento import CreateAgendamento
from src.services.agenda import AgendaClient


class AgendamentoClient(AgendaClient):
    def __init__(self, auth_token):
        super().__init__()
        self.base_url = self.base_url
        self.update_headers("Authorization", "Bearer " + auth_token)

    def create_agendamento(self, agendamento: CreateAgendamento):
        return self.post(f"agendamento/agendar", data=agendamento.model_dump())