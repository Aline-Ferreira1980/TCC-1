def string_to_dict(input_string):
    # Divida a string por vírgulas para obter os pares chave-valor
    pairs = input_string.split(',')

    result = {}
    for pair in pairs:
        key, value = pair.split(':')
        if "{" in value:
            # Se o valor contiver "{", significa que é um subdicionário
            value = string_to_dict(value.strip('{}'))
        result[key.strip()] = value

    return result
