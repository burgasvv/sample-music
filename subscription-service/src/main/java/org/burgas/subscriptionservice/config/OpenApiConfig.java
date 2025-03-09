package org.burgas.subscriptionservice.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        servers = @Server(url = "http://localhost:8765", description = "Gateway Server"),
        info = @Info(
                title = "Subscription Service", version = "1.0",
                description = "Сервис для подписок на платежный план"
        )
)
public class OpenApiConfig {
}
