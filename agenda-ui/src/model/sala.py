from decimal import Decimal

from pydantic import BaseModel, Field


class Sala(BaseModel):
    id: int = Field(...)
    nome: str = Field(...)
    local: str = Field(...)

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