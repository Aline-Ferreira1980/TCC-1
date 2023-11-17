from typing import List
from src.model.paciente import Paciente, AtualizaPacienteRequest


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

def to_atualiza_request(paciente: dict) -> AtualizaPacienteRequest:
    estag = paciente.copy()

    try:
        return AtualizaPacienteRequest(**paciente)
    except Exception as e:
        print(e)