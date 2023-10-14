from datetime import datetime
from decimal import Decimal
from typing import List

from pydantic import BaseModel, Field


class Payload(BaseModel):
    user_id: int
    user_name: str
    scope: List[str]
    user_nome: str
    authorities: List[str]
    jti: str
    client_id: str


class Header(BaseModel):
    alg: str
    typ: str


class Token(BaseModel):
    value: str = Field(...)
    header: Header
    payload: Payload
    signature: str = Field(...)


class OAuthResponseModel(BaseModel):
    access_token: Token
    token_type: str = Field(...)
    refresh_token: Token
    expires_in: int = Field(...)
    scope: str = Field(...)
    user_id: int = Field(...)
    user_nome: str = Field(...)
    jti: str = Field(...)

    class Config:
        use_enum_values = True
        json_encoders = {
            int: lambda v: str(v),
            float: lambda v: str(v),
            Decimal: lambda v: str(v)
        }
        dict_encoders = {
            int: lambda v: str(v),
            float: lambda v: str(v),
            Decimal: lambda v: str(v)
        }
