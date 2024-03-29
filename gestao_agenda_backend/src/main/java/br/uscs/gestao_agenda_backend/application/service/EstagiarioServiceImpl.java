package br.uscs.gestao_agenda_backend.application.service;

import br.uscs.gestao_agenda_backend.application.common.EstagiarioMapper;
import br.uscs.gestao_agenda_backend.application.common.HorarioTrabalhoMapper;
import br.uscs.gestao_agenda_backend.application.dto.EstagiarioResponse;
import br.uscs.gestao_agenda_backend.application.port.ConfirmacaoService;
import br.uscs.gestao_agenda_backend.application.port.EstagiarioService;
import br.uscs.gestao_agenda_backend.application.request.AtualizaEstagiarioRequest;
import br.uscs.gestao_agenda_backend.application.request.CadastroEstagiarioRequest;
import br.uscs.gestao_agenda_backend.application.request.HorarioTrabalhoRequest;
import br.uscs.gestao_agenda_backend.domain.model.*;
import br.uscs.gestao_agenda_backend.domain.model.enums.UserRole;
import br.uscs.gestao_agenda_backend.domain.port.DocenteRepository;
import br.uscs.gestao_agenda_backend.domain.port.EstagiarioRepository;
import br.uscs.gestao_agenda_backend.domain.port.PacienteRepository;
import br.uscs.gestao_agenda_backend.infrastructure.security.permissions.AppSecurity;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.common.exceptions.UnauthorizedUserException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EstagiarioServiceImpl implements EstagiarioService {


    private final EstagiarioMapper estagiarioMapper;
    private final HorarioTrabalhoMapper horarioTrabalhoMapper;

    private final EstagiarioRepository estagiarioRepository;
    private final DocenteRepository docenteRepository;
    private final ConfirmacaoService confirmacaoService;

    private final AppSecurity appSecurity;
    private final PacienteRepository pacienteRepository;

    @Override
    public EstagiarioResponse cadastrarEstagiario(CadastroEstagiarioRequest request) {
        Estagiario estagiario = estagiarioMapper.fromRequest(request);

        if (estagiarioRepository.findByEmail(estagiario.getEmail()).isPresent()) {
            throw new EntityExistsException("Email para estagiario ja esta em uso");
        }

        String senhaCriptografada = new BCryptPasswordEncoder().encode(estagiario.getSenha());

        estagiario.setSenha(senhaCriptografada);
        estagiario.setRole(UserRole.ESTAGIARIO);

        List<HorarioTrabalho> horariosTrabalho = createHorariosTrabalho(estagiario, request.getHorariosTrabalho());
        estagiario.setHorariosTrabalho(horariosTrabalho);

        estagiario.setConfirmed(false);
        String token = UUID.randomUUID().toString();
        estagiario.setToken(token);
        confirmacaoService.enviarEmailConfirmacao(estagiario.getEmail(), token);

        return estagiarioMapper.toResponse(estagiarioRepository.save(estagiario));
    }

    private List<HorarioTrabalho> createHorariosTrabalho(Estagiario estagiario,
                                                         List<HorarioTrabalhoRequest> horariosRequest) {

        if (horariosRequest != null) {
            List<HorarioTrabalho> horariosTrabalho = new ArrayList<>();
            for (HorarioTrabalhoRequest horarioRequest : horariosRequest) {
                horariosTrabalho.add(new HorarioTrabalho(
                        estagiario,
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
        this.validateEstagiarioAuthority(id);

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

            for(HorarioTrabalho horarios : estagiario.getHorariosTrabalho() ){
                horarios.setEstagiario(estagiario);
            }
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
        this.validateEstagiarioAuthority(id);

        if(estagiarioRepository.existsById(id)){
            estagiarioRepository.deleteById(id);
            estagiarioRepository.flush();
        }else throw new EntityNotFoundException("Nao foi possível encontrar o estagiario a ser deletado");
    }

    @Override
    public List<EstagiarioResponse> findByServico(String servicoAcronimo) {

        return estagiarioRepository.findByServicosAcronimo(servicoAcronimo)
                .stream()
                .map(estagiarioMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<EstagiarioResponse> addDocente(Long idEstagiario, Long idDocente) {
        this.validateEstagiarioAuthority(idEstagiario);
        Optional<Estagiario> estag = estagiarioRepository.findById(idEstagiario);
        Optional<Docente> doc = docenteRepository.findById(idDocente);


        if(estag.isPresent() && doc.isPresent()){
            Estagiario estagiario = estag.get();
            estagiario.setProfessorResponsavel(doc.get());
            return Optional.ofNullable(estagiarioMapper.toResponse(estagiarioRepository.save(estagiario)));
        }
        return Optional.empty();
    }

    @Override
    public Optional<EstagiarioResponse> removeDocente(Long idEstagiario) {
        this.validateEstagiarioAuthority(idEstagiario);
        Optional<Estagiario> estag = estagiarioRepository.findById(idEstagiario);

        if(estag.isPresent()){
            Estagiario estagiario = estag.get();
            estagiario.setProfessorResponsavel(null);
            return Optional.ofNullable(estagiarioMapper.toResponse(estagiarioRepository.save(estagiario)));
        }
        return Optional.empty();
    }

    @Override
    public List<EstagiarioResponse> findServicoEmpty() {
        return estagiarioRepository.findByServicosEmpty()
                .stream()
                .map(estagiarioMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<EstagiarioResponse> findDocenteNull() {
        return estagiarioRepository.findByProfessorResponsavelIsNull()
                .stream()
                .map(estagiarioMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<EstagiarioResponse> addPaciente(Long idEstagiario, Long idPaciente) {
        this.validateEstagiarioAuthority(idEstagiario);
        Optional<Estagiario> estag = estagiarioRepository.findById(idEstagiario);
        Optional<Paciente> paciente = pacienteRepository.findById(idPaciente);


        if(estag.isPresent() && paciente.isPresent()){
            Paciente pac = paciente.get();
            Estagiario estagiario = estag.get();
            pac.setEstagiario(estag.get());
            estagiario.addPaciente(pac);
            return Optional.ofNullable(estagiarioMapper.toResponse(estagiarioRepository.save(estagiario)));
        }
        return Optional.empty();
    }

    @Override
    public List<EstagiarioResponse> getAllAvailableInDataRange(LocalDate startDate, LocalDate endDate) {
        return null;
    }


    private void validateEstagiarioAuthority(Long userId, String...errorMessage){
        String message = errorMessage.length > 0 ?
                errorMessage[0] : "Estagiario somente tem permissão em seu proprio perfil";

        Long tokneUserId= appSecurity.getUserId();
        List<String> roles = appSecurity.getRoles();

        if(roles.contains("estagiario") && (!userId.equals(tokneUserId))){
            throw new UnauthorizedUserException(message);
        }
    }
    private void validateEstagiarioAuthority(String userEmail, String...errorMessage){
        String message = errorMessage.length > 0 ?
                errorMessage[0] : "Estagiario somente tem permissão em seu proprio perfil";

        String tokenUserEmail= appSecurity.getEmail();
        List<String> roles = appSecurity.getRoles();

        if(roles.contains("estagiario") && (!userEmail.equals(tokenUserEmail))){
            throw new UnauthorizedUserException(message);
        }
    }
}
