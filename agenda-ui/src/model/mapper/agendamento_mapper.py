from typing import List
from src.model.agendamento import Agendamento


def to_agendamentos(agendamentos: List[dict]) -> List[Agendamento]:
    result = []
    for agenda in agendamentos:
        try:
            result.append(Agendamento(**agenda))
        except Exception as e:
            print(e)
    return result


def to_agendamento(agendamento: dict) -> Agendamento:
    try:
        return Agendamento(**agendamento)
    except Exception as e:
        print(e)