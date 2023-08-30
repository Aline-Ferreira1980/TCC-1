package br.uscs.gestao_agenda_backend.application.common;

import br.uscs.gestao_agenda_backend.application.dto.in.CadastroPsicologoRequest;
import br.uscs.gestao_agenda_backend.application.dto.in.HorarioTrabalhoRequest;
import br.uscs.gestao_agenda_backend.application.dto.out.HorarioTrabalhoResponse;
import br.uscs.gestao_agenda_backend.application.dto.out.PsicologoResponse;
import br.uscs.gestao_agenda_backend.domain.model.HorarioTrabalho;
import br.uscs.gestao_agenda_backend.domain.model.Psicologo;

import java.util.ArrayList;
import java.util.List;

public class PsicologoMapper {

    public static Psicologo fromRequest(CadastroPsicologoRequest request){
        Psicologo psicologo = Psicologo.builder()
                .nomeSocial(request.getNome())
                .email(request.getEmail())
                .senha(request.getSenha())
                .especialidade(request.getEspecialidade())
                .build();

        if (request.getHorariosTrabalho() != null){
            List<HorarioTrabalho> horariosTrabalho = new ArrayList<>();

            for (HorarioTrabalhoRequest horarios: request.getHorariosTrabalho()){
                HorarioTrabalho tmpHorario = HorarioTrabalhoMapper.fromRequest(horarios);
                tmpHorario.setPsicologo(psicologo);
                horariosTrabalho.add(tmpHorario);
            }
            psicologo.setHorariosTrabalho(horariosTrabalho);

        }
        return psicologo;
    }

    public static PsicologoResponse toResponse(Psicologo psicologo) {
        PsicologoResponse response = PsicologoResponse.builder()
                .psicologoId(psicologo.getId())
                .nome(psicologo.getNomeSocial())
                .email(psicologo.getEmail())
                .especialidade(psicologo.getEspecialidade())
                .build();

        if (psicologo.getHorariosTrabalho() != null){
            List<HorarioTrabalhoResponse> horariosResponse = new ArrayList<>();
            for (HorarioTrabalho horarioTrabalho : psicologo.getHorariosTrabalho()){
                HorarioTrabalhoResponse tmpHoraro = HorarioTrabalhoMapper.toReponse(horarioTrabalho);
                horariosResponse.add(tmpHoraro);
            }
            response.setHorariosTrabalho(horariosResponse);
        }
        return response;
    }
}
