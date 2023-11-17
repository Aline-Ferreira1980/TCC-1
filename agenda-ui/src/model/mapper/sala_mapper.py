from typing import List

from src.model.sala import Sala


def to_salas(salas: List[dict]) -> List[Sala]:
    result = []
    for paciente in salas:
        try:
            result.append(Sala(**paciente))
        except Exception as e:
            print(e)
    return result


def to_sala(sala: dict) -> Sala:
    try:
        return Sala(**sala)
    except Exception as e:
        print(e)