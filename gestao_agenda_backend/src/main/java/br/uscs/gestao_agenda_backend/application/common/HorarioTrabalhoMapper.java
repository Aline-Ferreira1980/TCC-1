package br.uscs.gestao_agenda_backend.application.common;

import br.uscs.gestao_agenda_backend.application.request.HorarioTrabalhoRequest;
import br.uscs.gestao_agenda_backend.application.dto.HorarioTrabalhoResponse;
import br.uscs.gestao_agenda_backend.domain.model.HorarioTrabalho;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class HorarioTrabalhoMapper {


//    public static HorarioTrabalho fromRequest(HorarioTrabalhoRequest request){
//        return HorarioTrabalho.builder()
//                .diaSemana(request.getDiaSemana())
//                .horarioInicio(request.getHorarioInicio())
//                .horarioFim(request.getHorarioFim())
//                .build();
//    }
//
//    public static HorarioTrabalhoResponse toReponse(HorarioTrabalho horarioTrabalho){
//        return HorarioTrabalhoResponse.builder()
//                .diaSemana(horarioTrabalho.getDiaSemana())
//                .horarioInicio(horarioTrabalho.getHorarioInicio())
//                .horarioFim(horarioTrabalho.getHorarioFim())
//                .build();
//    }

    private ModelMapper modelMapper;

    public HorarioTrabalho fromRequest(HorarioTrabalhoRequest request){
        return modelMapper.map(request, HorarioTrabalho.class);
    }

    public HorarioTrabalhoResponse toReponse(HorarioTrabalho horarioTrabalho){
        return modelMapper.map(horarioTrabalho, HorarioTrabalhoResponse.class);
    }


    public List<HorarioTrabalho> fromRequestList(List<HorarioTrabalhoRequest> request){
        List<HorarioTrabalho> horariosTrabalho = new ArrayList<>();
        if (request != null){

            for (HorarioTrabalhoRequest horarios: request){
                HorarioTrabalho tmpHorario = fromRequest(horarios);
                horariosTrabalho.add(tmpHorario);
            }

        }
        return horariosTrabalho;
    }

    public List<HorarioTrabalhoResponse> toReponseList(List<HorarioTrabalho> horariosTrabalho) {
        return horariosTrabalho.stream()
                .map(this::toReponse)
                .collect(Collectors.toList());
    }
}
