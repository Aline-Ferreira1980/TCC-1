package br.uscs.gestao_agenda_backend.application.service;

import br.uscs.gestao_agenda_backend.application.common.EstagiarioMapper;
import br.uscs.gestao_agenda_backend.application.common.HorarioTrabalhoMapper;
import br.uscs.gestao_agenda_backend.application.dto.EstagiarioResponse;
import br.uscs.gestao_agenda_backend.application.port.EstagiarioService;
import br.uscs.gestao_agenda_backend.application.request.AtualizaEstagiarioRequest;
import br.uscs.gestao_agenda_backend.application.request.CadastroEstagiarioRequest;
import br.uscs.gestao_agenda_backend.application.request.HorarioTrabalhoRequest;
import br.uscs.gestao_agenda_backend.domain.model.Estagiario;
import br.uscs.gestao_agenda_backend.domain.model.HorarioTrabalho;
import br.uscs.gestao_agenda_backend.domain.model.enums.UserRole;
import br.uscs.gestao_agenda_backend.domain.port.EstagiarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EstagiarioServiceImpl implements EstagiarioService {


    private final EstagiarioMapper estagiarioMapper;
    private final HorarioTrabalhoMapper horarioTrabalhoMapper;

    private final EstagiarioRepository estagiarioRepository;


    @Override
    public EstagiarioResponse cadastrarEstagiario(CadastroEstagiarioRequest request) {
        Estagiario estagiario = estagiarioMapper.fromRequest(request);

        if (estagiarioRepository.findByEmail(estagiario.getEmail()).isPresent()) {
            // TODO: Criar uma exeção customizad
            throw new IllegalArgumentException("Email para estagiario ja esta em uso");
        }

        String senhaCriptografada = new BCryptPasswordEncoder().encode(estagiario.getSenha());

        estagiario.setSenha(senhaCriptografada);
        estagiario.setRole(UserRole.ESTAGIARIO);

        List<HorarioTrabalho> horariosTrabalho = createHorariosTrabalho(estagiario, request.getHorariosTrabalho());
        estagiario.setHorariosTrabalho(horariosTrabalho);


        return estagiarioMapper.toResponse(estagiarioRepository.save(estagiario));
    }

    private List<HorarioTrabalho> createHorariosTrabalho(Estagiario psicologo,
                                                         List<HorarioTrabalhoRequest> horariosRequest) {

        if (horariosRequest != null) {
            List<HorarioTrabalho> horariosTrabalho = new ArrayList<>();
            for (HorarioTrabalhoRequest horarioRequest : horariosRequest) {
                horariosTrabalho.add(new HorarioTrabalho(
                        psicologo,
                        horarioRequest.getDiaSemana(),
                        horarioRequest.getHorarioInicio(),
                        horarioRequest.getHorarioFim()));
            }
            return horariosTrabalho;

        }
        return null;


    }

    public List<EstagiarioResponse> findAll() {
        return estagiarioRepository.findAll()
                .stream()
                .map(estagiarioMapper::toResponse)
                .collect(Collectors.toList());
    }


    @Transactional
    @Override
    public Optional<EstagiarioResponse> updateEstagiario(Long id, AtualizaEstagiarioRequest request) {
        Optional<Estagiario> rs = estagiarioRepository.findById(id);

        if (rs.isPresent()) {
            Estagiario estagiario = rs.get();
            BeanUtils.copyProperties(request, estagiario,
                    "id",
                    "horariosTrabalho",
                    "agendamentos",
                    "professorResponsavel",
                    "servicos",
                    "pacientes");

            List<HorarioTrabalho> horariosTrabalho = horarioTrabalhoMapper.fromRequestList(request.getHorariosTrabalho());
            estagiario.getHorariosTrabalho().clear();
            estagiario.getHorariosTrabalho().addAll(horariosTrabalho);
            return Optional.ofNullable(estagiarioMapper.toResponse(estagiarioRepository.save(estagiario)));
        }
        return Optional.empty();

    }

    @Override
    public Optional<EstagiarioResponse> findById(Long id) {
        Optional<Estagiario> estagiario = estagiarioRepository.findById(id);
        return estagiario.map(estagiarioMapper::toResponse);
    }

    @Override
    public void deletaEstagiario(Long id) {
        estagiarioRepository.deleteById(id);
        estagiarioRepository.flush();
        // TODO: Mandar erro customizado
    }

    @Override
    public List<EstagiarioResponse> findByServico(String servicoAcronimo) {

        return estagiarioRepository.findByServicosAcronimo(servicoAcronimo)
                .stream()
                .map(estagiarioMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<EstagiarioResponse> getAllAvailableInDataRange(LocalDate startDate, LocalDate endDate) {
        return null;
    }
}
