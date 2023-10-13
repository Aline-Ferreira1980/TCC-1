from src.services.agenda import AgendaClient


class EstagiarioClient(AgendaClient):
    def __init__(self, auth_token):
        super().__init__()
        self.base_url = self.base_url + "/estagiario"
        self.update_headers("Authorization", "Bearer" + auth_token)

    def get_by_id(self, id):
        resp = self.get(f"/{id}").value
        return resp
