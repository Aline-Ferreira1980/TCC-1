from pydantic import Field
from pydantic_settings import BaseSettings, SettingsConfigDict


class AgendaClientConfig(BaseSettings):
    BASE_URL: str = Field(None, validation_alias="BASE_URL")
    CLIENT_ID: str = Field(None, validation_alias="CLIENT_ID")
    CLIENT_SECRET: str = Field(None, validation_alias="CLIENT_SECRET")

    model_config = SettingsConfigDict(env_file="../../.env", env_file_encoding='utf-8', extra='ignore')


class GlobalConfig(BaseSettings, case_sensitive=True):

    """Global configurations."""
    AGENDA_CLIENT: AgendaClientConfig = AgendaClientConfig()

    VERSION: str = Field("0.0.1", validation_alias="VERSION")
    APP_SECRET_KEY: str = Field("teste", validation_alias="APP_SECRET_KEY")
    model_config = SettingsConfigDict(env_file="../../.env", env_file_encoding='utf-8', extra='ignore')
