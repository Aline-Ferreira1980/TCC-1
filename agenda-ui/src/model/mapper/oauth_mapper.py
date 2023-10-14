import base64
from typing import Any

from src.model.mapper.utils import string_to_dict
from src.model.oauth_response import OAuthResponseModel


def to_oauth_response(value: dict) -> OAuthResponseModel:
    response = value.copy()
    response['access_token'] = _decode_token(response.get("access_token"))
    response['refresh_token'] = _decode_token(response.get("refresh_token"))
    try:
        oauth = OAuthResponseModel(**response)
        return oauth
    except Exception as e:
        print(e)


def _decode_token(token: str) -> dict:
    values = token.split('.')

    result = {
        "header": string_to_dict(base64.b64decode(values[0]).decode()),
        "payload": string_to_dict(base64.b64decode(values[1] + "=" * (-len(values[1]) % 4)).decode()), # Precisou de mais padding
        "signature": values[2],
        "value": token
    }
    return result

