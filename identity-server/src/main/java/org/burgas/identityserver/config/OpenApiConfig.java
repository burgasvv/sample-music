package org.burgas.identityserver.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        servers = @Server(url = "http://localhost:8765", description = "Gateway Server"),
        info = @Info(
                title = "Identity Server", version = "1.0",
                description = "Сервер безопасности и авторизации пользователя, " +
                              "который перенаправляет запросы на адрес других приложений"
        )
)
public class OpenApiConfig {
}
