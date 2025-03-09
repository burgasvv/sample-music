package org.burgas.soundservice.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        servers = @Server(url = "http://localhost:8765", description = "Gateway Server"),
        info = @Info(
                title = "Sound Service", version = "1.0",
                description = "Сервис для управления и организации работы со звуком, звуковыми пакетами и дорожками"
        )
)
public class OpenApiConfig {
}
