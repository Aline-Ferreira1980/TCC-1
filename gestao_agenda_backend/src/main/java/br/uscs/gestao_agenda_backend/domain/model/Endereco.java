package br.uscs.gestao_agenda_backend.domain.model;

import br.uscs.gestao_agenda_backend.application.dto.in.Telefone;
import lombok.*;
import org.springframework.security.access.method.P;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Endereco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Paciente paciente;

    private String ruaNumero;
    private String bairro;
    private String cidade;
    private String estado;
    private String cep;


}
