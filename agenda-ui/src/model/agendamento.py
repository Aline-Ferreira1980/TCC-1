from datetime import datetime
from decimal import Decimal

from pydantic import BaseModel, Field, field_serializer
from src.model.estagiario import EstagiarioProperty
from src.model.sala import Sala


class PacienteProperty(BaseModel):
    id: int = Field(...)
    nome: str = Field(...)
    nomeSocial: str = Field(...)
    email: str = Field(...)
    genero: str = Field(...)

class CreateAgendamento(BaseModel):
    estagiarioEmail: str = Field(...)
    pacienteEmail: str = Field(...)
    salaId: int = Field(...)
    inicioAgendamento: datetime
    fimAgendamento: datetime

    @field_serializer('inicioAgendamento')
    def serialize_dt(self, dt: datetime, _info):
        return dt.isoformat() + ".000Z"

    @field_serializer('fimAgendamento')
    def serialize_t(self, dt: datetime, _info):
        return dt.isoformat() + ".000Z"

    class Config:
        use_enum_values = True
        json_encoders = {
            int: lambda v: str(v),
            float: lambda v: str(v),
            Decimal: lambda v: str(v),
        }
        dict_encoders = {
            int: lambda v: str(v),
            float: lambda v: str(v),
            Decimal: lambda v: str(v),
        }


class Agendamento(BaseModel):
    id: int = Field(...)
    estagiario: EstagiarioProperty
    paciente: PacienteProperty
    sala: Sala
    inicioAgendamento: datetime
    fimAgendamento: datetime

    @field_serializer('inicioAgendamento')
    def serialize_dt(self, dt: datetime, _info):
        return dt.isoformat()

    @field_serializer('fimAgendamento')
    def serialize_t(self, dt: datetime, _info):
        return dt.isoformat()
