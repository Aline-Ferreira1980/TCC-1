from datetime import time
from decimal import Decimal
from typing import List, Optional
from pydantic import BaseModel, Field, field_serializer


class PacienteProperty(BaseModel):
    id: int = Field(...)
    nome: str = Field(...)
    nomeSocial: str = Field(...)
    email: str = Field(...)
    genero: str = Field(...)


class ServicoProperty(BaseModel):
    id: int = Field(...)
    acronimo: str = Field(...)
    nome: str = Field(...)
    descricao: str = Field(...)


class DocenteProperty(BaseModel):
    id: int = Field(...)
    nome: str = Field(...)
    sobrenome: str = Field(...)
    email: str = Field(...)


class HorarioTrabalho(BaseModel):
    id: int = Field(...)
    diaSemana: str = Field(...)
    horarioInicio: time = None
    horarioFim: time = None

    @field_serializer('horarioInicio')
    def serialize_dt(self, t: time, _info):
        # TODO: Preciso mudar para gerar no formado datetime.datetime.utcnow().strftime("%Y-%m-%dT%H:%M:%S.%fZ")
        # https://stackoverflow.com/questions/19654578/python-utc-datetime-objects-iso-format-doesnt-include-z-zulu-or-zero-offset
        return t.isoformat()

    @field_serializer('horarioFim')
    def serialize_t(self, t: time, _info):
        return t.isoformat()


class HorarioTrabalhoRequest(BaseModel):
    diaSemana: str = Field(...)
    horarioInicio: time = None
    horarioFim: time = None

    @field_serializer('horarioInicio')
    def serialize_dt(self, t: time, _info):
        return t.isoformat()

    @field_serializer('horarioFim')
    def serialize_t(self, t: time, _info):
        return t.isoformat()


class Estagiario(BaseModel):
    id: int = Field(...)
    nome: str = Field(...)
    sobrenome: str = Field(...)
    email: str = Field(...)
    ra: str = Field(...)
    turno: str = Field(...)
    turma: str = Field(...)
    semestre: int = Field(...)
    pacientes: List[PacienteProperty]
    servicos: List[ServicoProperty]
    professorResponsavel: Optional[DocenteProperty]
    horariosTrabalho: List[HorarioTrabalho]

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


class AtualizaEstagiarioRequest(BaseModel):
    nome: str = Field(...)
    sobrenome: str = Field(...)
    email: str = Field(...)
    ra: str = Field(...)
    turno: str = Field(...)
    turma: str = Field(...)
    semestre: int = Field(...)
    horariosTrabalho: List[HorarioTrabalhoRequest]

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


class EstagiarioProperty(BaseModel):
    id: int = Field(...)
    nome: str = Field(...)
    sobrenome: str = Field(...)
    email: str = Field(...)
    ra: str = Field(...)
    turma: str = Field(...)

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


class CadastraEstagiarioRequest(BaseModel):
    nome: str = Field(...)
    sobrenome: str = Field(...)
    email: str = Field(...)
    senha: str = Field(...)
    ra: str = Field(...)
    turno: str = Field(...)
    turma: str = Field(...)
    semestre: int = Field(...)
    horariosTrabalho: List[HorarioTrabalhoRequest]

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