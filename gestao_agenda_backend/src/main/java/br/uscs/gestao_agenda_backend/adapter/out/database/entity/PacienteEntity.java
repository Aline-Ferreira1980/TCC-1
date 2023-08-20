package br.uscs.gestao_agenda_backend.adapter.out.database.entity;

import com.amazonaws.services.s3.model.EmailAddressGrantee;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Paciente")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PacienteEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_paciente")
    private Integer idPaciente;

    @Column(name = "nome_social")
    private String nomeSocial;

    @DateTimeFormat(pattern="yyyy-mm-dd")
    @Column(name = "data_nascimento")
    private Date dataNascimento;

    @Column(name = "genero")
    private String genero;

    @Column(name = "estado_civil")
    private String estadoCivil;

    @Column(name = "etnia_racial")
    private String etniaRacial;

    @OneToOne(mappedBy = "idPaciente")
    private EnderecoEntity endereco;

    @OneToMany(mappedBy = "idPaciente")
    private List<TelefoneEntity> telefone;

    @Column(name = "portador_deficiencia")
    private boolean portador_deficiencia;

    @Column(name = "escolaridade")
    private String escolaridade;

    @Column(name = "atividade_profissional")
    private String atividade_profissional;

    @OneToMany(mappedBy = "idPaciente")
    private List<MoradorCasaEntity> moradoresCasa;

    @Column(name = "responsavel_legal")
    private String responsavelLegal;

    @Column(name = "email", nullable = false)
    private EmailAddressGrantee email;

    @OneToOne(mappedBy = "idPaciente")
    private AlunoUscsEntity alunoUscs;

    @Column(name = "disponibilidade_atendimento")
    private String disponibilidadeAtendimento;

    @Column(name = "como_conheceu_servico")
    private String comoConheceuServicoEscola;

    @Column(name = "ja_foi_atendido_servico")
    private boolean jaFoiAtendidoServicoEscola;

    @Column(name = "possui_encaminhamento")
    private boolean possuiEncaminhamento;

    @Column(name = "motivo_queixa_atendimento")
    private String motivoAtendimentoQueixa;

    @Column(name = "status_atendimento")
    private String statusAtendimento;

}
