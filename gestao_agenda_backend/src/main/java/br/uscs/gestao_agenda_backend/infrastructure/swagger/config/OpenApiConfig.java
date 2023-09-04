package br.uscs.gestao_agenda_backend.infrastructure.swagger.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@OpenAPIDefinition(
        info = @Info(
                description = "API de gerenciamento para CESEP-USCS",
                title = "Documentaçao da API",
                version = "0.0.1"
        ),
        servers = {
                @Server(
                        description = "Local Host",
                        url = "http://localhost:8080"
                )
        },
        security = { @SecurityRequirement(name = "BearerAuth") }
)
@SecurityScheme(
        name = "BearerAuth",
        description = "Autenticação com JWT",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)
public class OpenApiConfig {

}
