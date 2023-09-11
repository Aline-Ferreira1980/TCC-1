package br.uscs.gestao_agenda_backend.infrastructure.swagger.config;

import br.uscs.gestao_agenda_backend.domain.model.enums.Turno;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.media.StringSchema;
import org.springdoc.core.customizers.OpenApiCustomiser;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;


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

@Configuration
public class OpenApiConfig {
        @Bean
        public OpenApiCustomiser customOpenApi() {
                return openApi -> {
                        Schema<?> localTimeSchema = new StringSchema()
                                .format("HH:mm:ss")
                                .example("08:00:00")
                                .description("Definiçao de horario no formado HH:mm:ss");

                        openApi.getComponents().addSchemas("LocalTime", localTimeSchema);

                        Schema<?> locaDateTimeSchema = new StringSchema()
                                .format("YYYY-mm-dd'T'HH:mm:ss")
                                .example("2023-09-15T14:00:00")
                                .description("Definiçao de data e hora seguindo o formato ISO 8601 YYYY-mm-dd'T'HH:mm:ss");

                        openApi.getComponents().addSchemas("LocalDateTime", locaDateTimeSchema);


                };
        }

        @Bean
        public OperationCustomizer customOperation() {
                return (operation, handlerMethod) -> {
                        // Customize individual operations here if needed
                        return operation;
                };
        }
}
