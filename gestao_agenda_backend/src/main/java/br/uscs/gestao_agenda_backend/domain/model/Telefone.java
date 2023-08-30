package br.uscs.gestao_agenda_backend.domain.model;

import lombok.*;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Telefone {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Paciente paciente;
    private String telefoneCelular;
    private String telefoneFixo;
    private String telefoneEmergencia;
    private String nomeContatoEmergencia;
}
