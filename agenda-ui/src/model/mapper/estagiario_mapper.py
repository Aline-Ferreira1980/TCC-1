from src.model.estagiario import Estagiario, AtualizaEstagiarioRequest

dias_da_semana = {
    "monday": 'Segunda',
    "tuesday": 'Terça',
    "wednesday": 'Quarta',
    "thursday": 'Quinta',
    "friday": 'Sexta',
    "saturday": 'Sabado',
    "sunday": 'Domingo'
}


def to_estagiario(estagiario: dict) -> Estagiario:
    estag = estagiario.copy()

    for horario in estag['horariosTrabalho']:
        horario['diaSemana'] = dias_da_semana[horario['diaSemana'].lower()]
    try:
        estag = Estagiario(**estag)
        return estag
    except Exception as e:
        print(e)


def to_atualiza_request(estagiario: dict) -> AtualizaEstagiarioRequest:
    estag = estagiario.copy()

    for horario in estag['horariosTrabalho']:
        horario['diaSemana'] = dias_da_semana[horario['diaSemana'].lower()]
    try:
        return AtualizaEstagiarioRequest(**estag)
    except Exception as e:
        print(e)

