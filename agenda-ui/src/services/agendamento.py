from src.model.agendamento import CreateAgendamento
from src.services.agenda import AgendaClient, HTTPResponse


class AgendamentoClient(AgendaClient):
    def __init__(self, auth_token):
        super().__init__()
        self.base_url = self.base_url
        self.update_headers("Authorization", "Bearer " + auth_token)

    def create_agendamento(self, agendamento: CreateAgendamento):
        return self.post(f"agendamento/agendar", data=agendamento.model_dump())

    def find_by_user_id(self, id: int):
        return self.get(f"agendamento/user/{id}")

    def delete_agendandamento(self, id: int):
        return self.delete(f"agendamento/{id}")

    def find_by_id(self, id_agendamento) -> HTTPResponse:
        return self.get(f"agendamento/{id_agendamento}")

    def update(self, id_agendamento: int, agendamento: CreateAgendamento):
        return self.put(f"agendamento/{id_agendamento}", data=agendamento.model_dump())