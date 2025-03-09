package org.burgas.producerservice.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        servers = @Server(url = "http://localhost:8765", description = "Gateway Server"),
        info = @Info(
                title = "Producer Server", version = "1.0",
                description = "Сервис для организации работы продюсеров, " +
                              "загрузки своего музыкального контента"
        )
)
public class OpenApiConfig {
}
