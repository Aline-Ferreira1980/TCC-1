from src.model.estagiario import Estagiario

dias_da_semana = {
    "monday": 0,
    "tuesday": 1,
    "wednesday": 2,
    "thursday": 3,
    "friday": 4,
    "saturday": 5,
    "sunday": 6
}


def to_estagiario(estagiario: dict) -> Estagiario:
    estag = estagiario.copy()

    for horario in estag['horariosTrabalho']:
        horario['diaSemana'] = dias_da_semana[horario['diaSemana'].lower()]
    try:
        return Estagiario(**estag)
    except Exception as e:
        print(e)
