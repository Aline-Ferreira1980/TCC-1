package br.uscs.gestao_agenda_backend.application.common;

import br.uscs.gestao_agenda_backend.application.dto.in.HorarioTrabalhoRequest;
import br.uscs.gestao_agenda_backend.application.dto.out.HorarioTrabalhoResponse;
import br.uscs.gestao_agenda_backend.domain.model.HorarioTrabalho;

import java.util.ArrayList;
import java.util.List;

public class HorarioTrabalhoMapper {


    public static HorarioTrabalho fromRequest(HorarioTrabalhoRequest request){
        return HorarioTrabalho.builder()
                .diaSemana(request.getDiaSemana())
                .horarioInicio(request.getHorarioInicio())
                .horarioFim(request.getHorarioFim())
                .build();
    }

    public static HorarioTrabalhoResponse toReponse(HorarioTrabalho horarioTrabalho){
        return HorarioTrabalhoResponse.builder()
                .diaSemana(horarioTrabalho.getDiaSemana())
                .horarioInicio(horarioTrabalho.getHorarioInicio())
                .horarioFim(horarioTrabalho.getHorarioFim())
                .build();
    }

    public static List<HorarioTrabalho> fromRequestList(List<HorarioTrabalhoRequest> request){
        List<HorarioTrabalho> horariosTrabalho = new ArrayList<>();
        if (request != null){

            for (HorarioTrabalhoRequest horarios: request){
                HorarioTrabalho tmpHorario = HorarioTrabalhoMapper.fromRequest(horarios);
                horariosTrabalho.add(tmpHorario);
            }

        }
        return horariosTrabalho;
    }
}
