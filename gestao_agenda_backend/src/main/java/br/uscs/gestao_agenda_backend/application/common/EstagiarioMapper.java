package br.uscs.gestao_agenda_backend.application.common;

import br.uscs.gestao_agenda_backend.application.dto.EstagiarioPropertyResponse;
import br.uscs.gestao_agenda_backend.application.request.CadastroEstagiarioRequest;
import br.uscs.gestao_agenda_backend.application.dto.EstagiarioResponse;
import br.uscs.gestao_agenda_backend.domain.model.Estagiario;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class EstagiarioMapper {

//    public static Estagiario fromRequest(CadastroEstagiarioRequest request){
//        Estagiario psicologo = Estagiario.builder()
//                .nome(request.getNome())
//                .email(request.getEmail())
//                .senha(request.getSenha())
//                .especialidade(request.getEspecialidade())
//                .build();
//
//        if (request.getHorariosTrabalho() != null){
//            List<HorarioTrabalho> horariosTrabalho = new ArrayList<>();
//
//            for (HorarioTrabalhoRequest horarios: request.getHorariosTrabalho()){
//                HorarioTrabalho tmpHorario = HorarioTrabalhoMapper.fromRequest(horarios);
//                tmpHorario.setEstagiario(psicologo);
//                horariosTrabalho.add(tmpHorario);
//            }
//            psicologo.setHorariosTrabalho(horariosTrabalho);
//
//        }
//        return psicologo;
//    }
//
//    public static EstagiarioResponse toResponse(Estagiario estagiario) {
//        EstagiarioResponse response = EstagiarioResponse.builder()
//                .psicologoId(estagiario.getId())
//                .nome(estagiario.getNome())
//                .email(estagiario.getEmail())
//                .build();
//
//        if (estagiario.getHorariosTrabalho() != null){
//            List<HorarioTrabalhoResponse> horariosResponse = new ArrayList<>();
//            for (HorarioTrabalho horarioTrabalho : estagiario.getHorariosTrabalho()){
//                HorarioTrabalhoResponse tmpHoraro = HorarioTrabalhoMapper.toReponse(horarioTrabalho);
//                horariosResponse.add(tmpHoraro);
//            }
//            response.setHorariosTrabalho(horariosResponse);
//        }
//        return response;
//    }

    private ModelMapper modelMapper;

    public Estagiario fromRequest(CadastroEstagiarioRequest request){
        return modelMapper.map(request, Estagiario.class);
    }

    public EstagiarioResponse toResponse(Estagiario estagiario){
        return modelMapper.map(estagiario, EstagiarioResponse.class);
    }
    public EstagiarioPropertyResponse toPropertyResponse(Estagiario estagiario){
        return modelMapper.map(estagiario, EstagiarioPropertyResponse.class);
    }



}