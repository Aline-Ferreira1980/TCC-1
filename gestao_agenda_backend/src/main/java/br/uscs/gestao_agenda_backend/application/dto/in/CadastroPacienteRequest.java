package br.uscs.gestao_agenda_backend.application.dto.in;

import br.uscs.gestao_agenda_backend.domain.model.Paciente;
import br.uscs.gestao_agenda_backend.domain.model.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CadastroPacienteRequest {

    private String nomeSocial;
    private Date dataNascimento;
    private String genero;
    private String estadoCivil;
    private String etniaRacial;
    private String senha;
    private String nomeRegistro;
    private List<Endereco> idEndereco;
    private List<Telefone> telefone;
    private boolean portadorDeficiencia;
    private String email;

}
