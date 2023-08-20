package br.uscs.gestao_agenda_backend.adapter.out.database.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "Telefone")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TelefoneEntity {


    @Id
    @Column(name = "id_paciente")
    private PacienteEntity idPaciente;
    @Column(name = "tel_cel")
    private String telefoneCelular;
    @Column(name = "tel_fixo")
    private String telefoneFixo;
    @Column(name = "tel_emerg")
    private String telefoneEmergencia;
    @Column(name = "nome_contat_tel_emerg")
    private String nomeContatoEmergencia;

}
