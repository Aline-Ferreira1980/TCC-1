package br.uscs.gestao_agenda_backend.application.service;

import br.uscs.gestao_agenda_backend.application.common.HorarioTrabalhoMapper;
import br.uscs.gestao_agenda_backend.application.common.EstagiarioMapper;
import br.uscs.gestao_agenda_backend.application.request.CadastroEstagiarioRequest;
import br.uscs.gestao_agenda_backend.application.request.HorarioTrabalhoRequest;
import br.uscs.gestao_agenda_backend.application.request.UpdateEstagiarioRequest;
import br.uscs.gestao_agenda_backend.application.dto.EstagiarioResponse;
import br.uscs.gestao_agenda_backend.application.port.EstagiarioService;
import br.uscs.gestao_agenda_backend.domain.model.HorarioTrabalho;
import br.uscs.gestao_agenda_backend.domain.model.Estagiario;
import br.uscs.gestao_agenda_backend.domain.model.enums.UserRole;
import br.uscs.gestao_agenda_backend.domain.port.EstagiarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
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

        if (estagiarioRepository.findByEmail(estagiario.getEmail()) != null) {
            // TODO: Criar uma exeção customizad
            throw new RuntimeException("O email já está em uso.");
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

        if(horariosRequest != null){
            List<HorarioTrabalho> horariosTrabalho = new ArrayList<>();
            for(HorarioTrabalhoRequest horarioRequest: horariosRequest){
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

    public List<EstagiarioResponse> findAll(){
        return estagiarioRepository.findAll()
                .stream()
                .map(estagiarioMapper::toResponse)
                .collect(Collectors.toList());
    }


    @Override
    public EstagiarioResponse updateEstagiario(UpdateEstagiarioRequest request) {
        Estagiario estagiario = estagiarioRepository.getById(request.getId());
        BeanUtils.copyProperties(request, estagiario,"id", "horariosTrabalho");

        List<HorarioTrabalho> horariosTrabalho = horarioTrabalhoMapper.fromRequestList(request.getHorariosTrabalho());
        estagiario.setHorariosTrabalho(horariosTrabalho);

        estagiarioRepository.save(estagiario);
        return estagiarioMapper.toResponse(estagiario);

    }


    @Override
    public List<EstagiarioResponse> getAllAvailableInDataRange(LocalDate startDate, LocalDate endDate) {
        return null;
    }
}
