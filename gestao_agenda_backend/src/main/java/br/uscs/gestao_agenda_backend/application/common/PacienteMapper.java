package br.uscs.gestao_agenda_backend.application.common;

import br.uscs.gestao_agenda_backend.application.dto.in.CadastroPacienteRequest;
import br.uscs.gestao_agenda_backend.application.dto.in.Endereco;
import br.uscs.gestao_agenda_backend.application.dto.in.Telefone;
import br.uscs.gestao_agenda_backend.application.dto.out.PacienteResponse;
import br.uscs.gestao_agenda_backend.domain.model.Paciente;
import br.uscs.gestao_agenda_backend.domain.model.UserRole;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class PacienteMapper {


    static public Paciente fromRequest(CadastroPacienteRequest request) {

        List<Endereco> endereco = new ArrayList<>();
        List <Telefone> telefone = new ArrayList<>();

        return Paciente.builder()
                .nomeRegistro(request.getNomeRegistro())
                .nomeSocial(request.getNomeSocial())
                .dataNascimento(request.getDataNascimento())
                .genero(request.getGenero())
                .estadoCivil(request.getEstadoCivil())
                .etniaRacial(request.getEtniaRacial())
                .endereco(Endereco.builder()
//                        .ruaNumero(endereco.getRuaNumero())
//                        .bairro(endereco.getBairro())
//                        .cidade(endereco.getCidade())
//                        .estado(endereco.getEstado())
//                        .cep(endereco.getCep())
                        .build())
                .telefone(Collections.singletonList(Telefone.builder()
//                        .telefoneCelular(telefone.getTelefoneCelular())
//                        .telefoneFixo(telefone.getTelefoneFixo())
//                        .telefoneEmergencia(telefone.getTelefoneEmergencia())
//                        .nomeContatoEmergencia(telefone.getNomeContatoEmergencia())
                        .build()))
                .portadorDeficiencia(request.isPortadorDeficiencia())
                .email(request.getEmail())
                .senha(request.getSenha())
                .role(UserRole.PACIENTE)
                .build();
    }

    static public PacienteResponse toResponse(Paciente paciente) {

        Endereco endereco = new Endereco();
        Telefone telefone = new Telefone();

        return PacienteResponse.builder()
                .nomeRegistro(paciente.getNomeRegistro())
                .nomeSocial(paciente.getNomeSocial())
                .dataNascimento(paciente.getDataNascimento())
                .genero(paciente.getGenero())
                .estadoCivil(paciente.getEstadoCivil())
                .etniaRacial(paciente.getEtniaRacial())
                .endereco(Collections.singletonList(Endereco.builder()
                        .ruaNumero(endereco.getRuaNumero())
                        .bairro(endereco.getBairro())
                        .cidade(endereco.getCidade())
                        .estado(endereco.getEstado())
                        .cep(endereco.getCep())
                        .build()))
                .telefone(Collections.singletonList(Telefone.builder()
                                .telefoneCelular(telefone.getTelefoneCelular())
                                .telefoneFixo(telefone.getTelefoneFixo())
                                .telefoneEmergencia(telefone.getNomeContatoEmergencia())
                                .nomeContatoEmergencia(telefone.getNomeContatoEmergencia())
                        .build()))
                .portadorDeficiencia(paciente.isPortadorDeficiencia())
                .build();
    }
}
