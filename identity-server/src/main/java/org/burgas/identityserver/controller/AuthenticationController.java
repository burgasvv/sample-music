package org.burgas.identityserver.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.burgas.identityserver.dto.IdentityPrincipal;
import org.burgas.identityserver.dto.IdentityResponse;
import org.burgas.identityserver.mapper.IdentityPrincipalMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@Controller
@RequestMapping(value = "/authentication")
@Tag(
        name = "AuthenticationController",
        description = "Контролер для получения аутентификации пользователя"
)
public class AuthenticationController {

    private final IdentityPrincipalMapper identityPrincipalMapper;

    public AuthenticationController(IdentityPrincipalMapper identityPrincipalMapper) {
        this.identityPrincipalMapper = identityPrincipalMapper;
    }

    @GetMapping(value = "/principal")
    @Operation(
            summary = "Объект Principal",
            description = "Метод получения объекта Principal"
    )
    public @ResponseBody ResponseEntity<IdentityPrincipal> getPrincipal(Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            return ResponseEntity
                    .ok(identityPrincipalMapper
                            .toIdentityPrincipal((IdentityResponse) authentication.getPrincipal(), true));
        } else {
            return ResponseEntity
                    .ok(IdentityPrincipal.builder().username("anonymous").authenticated(false).build());
        }
    }

    @Operation(summary = "Получение csrf xor токена для выполнения запросов изменения")
    @GetMapping(value = "/csrf-token")
    public ResponseEntity<CsrfToken> getCsrfToken(HttpServletRequest request) {
        return ResponseEntity
                .status(OK)
                .contentType(APPLICATION_JSON)
                .body((CsrfToken) request.getAttribute("_csrf"));
    }
}
