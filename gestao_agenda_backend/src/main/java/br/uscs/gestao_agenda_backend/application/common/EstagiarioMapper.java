package br.uscs.gestao_agenda_backend.application.common;

import br.uscs.gestao_agenda_backend.application.dto.EstagiarioPropertyResponse;
import br.uscs.gestao_agenda_backend.application.dto.EstagiarioResponse;
import br.uscs.gestao_agenda_backend.application.request.CadastroEstagiarioRequest;
import br.uscs.gestao_agenda_backend.domain.model.Estagiario;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class EstagiarioMapper {

    private ModelMapper modelMapper;


    private DocenteMapper docenteMapper;
    private HorarioTrabalhoMapper horarioTrabalhoMapper;
    private SalaMapper salaMapper;

    public Estagiario fromRequest(CadastroEstagiarioRequest request) {
        return modelMapper.map(request, Estagiario.class);
    }

    public EstagiarioResponse toResponse(Estagiario estagiario) {
        ServicoMapper servicoMapper = new ServicoMapper(this.modelMapper, this);
        PacienteMapper pacienteMapper = new PacienteMapper(this.modelMapper, this);
        AgendamentoMapper agendamentoMapper = new AgendamentoMapper(
                this.modelMapper, this, pacienteMapper, this.salaMapper);

        return EstagiarioResponse.builder()
                .id(estagiario.getId())
                .nome(estagiario.getNome())
                .sobrenome(estagiario.getSobrenome())
                .email(estagiario.getEmail())
                .ra(estagiario.getRa())
                .turno(estagiario.getTurno())
                .turma(estagiario.getTurma())
                .semestre(estagiario.getSemestre())
                .pacientes(pacienteMapper.toPropertyResponseSet(estagiario.getPacientes()))
                .servicos(servicoMapper.toPropertyResponseSet(estagiario.getServicos()))
                .professorResponsavel(docenteMapper.toPropertyResponse(estagiario.getProfessorResponsavel()))
                .horariosTrabalho(horarioTrabalhoMapper.toReponseList(estagiario.getHorariosTrabalho()))
                .build();

    }

    public EstagiarioPropertyResponse toPropertyResponse(Estagiario estagiario) {
        if (estagiario != null) {

            return modelMapper.map(estagiario, EstagiarioPropertyResponse.class);
        }
        return null;
    }

}