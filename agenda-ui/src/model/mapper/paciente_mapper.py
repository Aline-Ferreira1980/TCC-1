from typing import List
from src.model.paciente import Paciente


def to_pacientes(pacientes: List[dict]) -> List[Paciente]:
    result = []
    for paciente in pacientes:
        try:
            result.append(Paciente(**paciente))
        except Exception as e:
            print(e)
    return result


def to_paciente(paciente: dict) -> Paciente:
    try:
        return Paciente(**paciente)
    except Exception as e:
        print(e)