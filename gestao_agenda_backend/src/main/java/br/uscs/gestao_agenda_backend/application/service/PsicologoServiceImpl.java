package br.uscs.gestao_agenda_backend.application.service;

import br.uscs.gestao_agenda_backend.application.common.HorarioTrabalhoMapper;
import br.uscs.gestao_agenda_backend.application.common.PsicologoMapper;
import br.uscs.gestao_agenda_backend.application.dto.in.CadastroPsicologoRequest;
import br.uscs.gestao_agenda_backend.application.dto.in.HorarioTrabalhoRequest;
import br.uscs.gestao_agenda_backend.application.dto.in.UpdatePsicologoRequest;
import br.uscs.gestao_agenda_backend.application.dto.out.PsicologoResponse;
import br.uscs.gestao_agenda_backend.application.port.PsicologoService;
import br.uscs.gestao_agenda_backend.domain.model.HorarioTrabalho;
import br.uscs.gestao_agenda_backend.domain.model.Psicologo;
import br.uscs.gestao_agenda_backend.domain.model.UserRole;
import br.uscs.gestao_agenda_backend.domain.port.PsicologoRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class PsicologoServiceImpl  implements PsicologoService {

    private final PsicologoRepository psicologoRepository;

    public PsicologoServiceImpl(PsicologoRepository psicologoRepository){
        this.psicologoRepository = psicologoRepository;
    }

    @Override
    public void cadastrarPsicologo(CadastroPsicologoRequest request) {
        if (psicologoRepository.findByEmail(request.getEmail()) != null) {
            throw new RuntimeException("O email já está em uso.");
        }

        // TODO: Implementar criptografia de senha
        String senhaCriptografada = new BCryptPasswordEncoder().encode(request.getSenha());



        Psicologo psicologo = new Psicologo();
        psicologo.setNome(request.getNome());
        psicologo.setEmail(request.getEmail());
        psicologo.setSenha(senhaCriptografada);
        psicologo.setEspecialidade(request.getEspecialidade());
        psicologo.setRole(UserRole.PSICOLOGO);

        List<HorarioTrabalho> horariosTrabalho = createHorariosTrabalho(psicologo, request.getHorariosTrabalho());
        psicologo.setHorariosTrabalho(horariosTrabalho);

        psicologoRepository.save(psicologo);
    }

    private List<HorarioTrabalho> createHorariosTrabalho(Psicologo psicologo, List<HorarioTrabalhoRequest> horariosRequest) {
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

    public List<PsicologoResponse> findAll(){
        return psicologoRepository.findAll().stream().map(PsicologoMapper::toResponse).toList();
    }

    @Override
    public PsicologoResponse uodatePsicologo(UpdatePsicologoRequest request) {
        Psicologo psicologo = psicologoRepository.getById(request.getId());
        psicologo.setNome(request.getNome());
        psicologo.setEspecialidade(request.getEspecialidade());

        List<HorarioTrabalho> horariosTrabalho = HorarioTrabalhoMapper.fromRequestList(request.getHorariosTrabalho());
        psicologo.setHorariosTrabalho(horariosTrabalho);

        psicologoRepository.save(psicologo);

        return PsicologoMapper.toResponse(psicologo);
    }

//    @Override
//    public PsicologoResponse updateHorariosTrabalho(Long psicologoID,
//                                                    List<HorarioTrabalhoRequest> horariosTrabalhoRequest) {
//
//        Psicologo psicologo = psicologoRepository.getById(psicologoID);
//
//        List<HorarioTrabalho> horariosTrabalho = HorarioTrabalhoMapper.fromRequestList(horariosTrabalhoRequest);
//
//        // Todo nao funciona
//        horariosTrabalho.addAll(psicologo.getHorariosTrabalho());
//
//        List<HorarioTrabalho> updated = (List<HorarioTrabalho>) horariosTrabalho.stream().distinct().toList();
//
//        psicologo.setHorariosTrabalho(updated);
//
//        return PsicologoMapper.toResponse(psicologo);
//
//    }


    @Override
    public List<PsicologoResponse> getAllAvailableInDataRange(LocalDate startDate, LocalDate endDate) {
        return null;
    }
}
