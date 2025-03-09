package org.burgas.imageservice.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import org.burgas.imageservice.dto.IdentityPrincipal;
import org.burgas.imageservice.exception.IdentityNotAuthorizedException;
import org.burgas.imageservice.handler.RestClientHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Objects;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.burgas.imageservice.entity.ImageMessage.NOT_AUTHORIZED_NOT_AUTHENTICATED;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@WebFilter(
        urlPatterns = {
                "/images/identity/upload-image",
                "/images/identity/change-image",
                "/images/identity/delete-image",
        }
)
public class IdentityImageFilter implements Filter {

    private final RestClientHandler restClientHandler;

    public IdentityImageFilter(RestClientHandler restClientHandler) {
        this.restClientHandler = restClientHandler;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        String authentication = httpServletRequest.getHeader(AUTHORIZATION);
        Long identityId = Long.parseLong(
                new String(httpServletRequest.getPart("identityId").getInputStream().readAllBytes(), UTF_8)
        );

        IdentityPrincipal identityPrincipal = restClientHandler.getPrincipal(authentication).getBody();
        if (
                Objects.requireNonNull(identityPrincipal).getAuthenticated() &&
                identityPrincipal.getId().equals(identityId)
        ) {
            filterChain.doFilter(servletRequest, servletResponse);

        } else {
            throw new IdentityNotAuthorizedException(NOT_AUTHORIZED_NOT_AUTHENTICATED.getMessage());
        }
    }
}
