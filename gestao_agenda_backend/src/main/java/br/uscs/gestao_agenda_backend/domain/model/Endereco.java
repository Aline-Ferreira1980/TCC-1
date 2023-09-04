package br.uscs.gestao_agenda_backend.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Endereco {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;

    private String rua;
    private String cidade;
    private String bairro;
    private String cep;

}
