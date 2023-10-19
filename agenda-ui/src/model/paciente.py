from datetime import datetime, date
from decimal import Decimal
from typing import Optional, List

from pydantic import BaseModel, Field, field_serializer

from src.model.estagiario import EstagiarioProperty


class Endereco(BaseModel):
    rua: str = Field(...)
    cidade: str = Field(...)
    bairro: str = Field(...)
    cep: str = Field(...)

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


class Telefone(BaseModel):
    tipo: str = Field(...)
    telefone: str = Field(...)

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


class Paciente(BaseModel):
    id: int = Field(...)
    nome: str = Field(...)
    sobrenome: str = Field(...)
    email: str = Field(...)
    nomeSocial: str = Field(...)
    dataNascimento: date
    genero: str = Field(...)
    estadoCivil: str = Field(...)
    etniaRacial: str = Field(...)
    endereco: Endereco
    telefone: List[Telefone]
    isMenorIdade: bool
    relacaoRepresentante: Optional[str] = Field(...)
    representanteNome: Optional[str] = Field(...)
    estagiario: Optional[EstagiarioProperty] = None

    @field_serializer('dataNascimento')
    def serialize_t(self, d: date, _info):
        return d.isoformat()

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