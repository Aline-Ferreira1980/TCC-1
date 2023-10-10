from pydantic import Field
from pydantic_settings import BaseSettings, SettingsConfigDict


class AgendaClient(BaseSettings):
    BASE_URL: str = Field(None, validation_alias="BASE_URL")
    CLIENT_ID: str = Field(None, validation_alias="CLIENT_ID")
    CLIENT_SECRET: str = Field(None, validation_alias="CLIENT_SECRET")

    model_config = SettingsConfigDict(env_file="../../.env", env_file_encoding='utf-8', extra='ignore')


class GlobalConfig(BaseSettings, case_sensitive=True):

    """Global configurations."""
    AGENDA_CLIENT: AgendaClient = AgendaClient()
    # PG_CONFIG: PushgatewayConfig = PushgatewayConfig()
    #
    # ENV: str = Field('dev', validation_alias="ENV")

    VERSION: str = Field("0.0.1", validation_alias="VERSION")
    model_config = SettingsConfigDict(env_file="../../.env", env_file_encoding='utf-8', extra='ignore')
