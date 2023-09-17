package br.uscs.gestao_agenda_backend;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDateTime;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Agenda Psicologia",
        version = "0.0.1",
        description = "API desenvolvida para gerenciamento dos agendametos da clinica de Psicologia da USCS"))
public class Application {


    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);

        LocalDateTime.now();
    }

}
