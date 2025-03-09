package org.burgas.imageservice.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import org.burgas.imageservice.dto.IdentityPrincipal;
import org.burgas.imageservice.dto.ProducerResponse;
import org.burgas.imageservice.exception.IdentityNotAuthorizedException;
import org.burgas.imageservice.exception.ProducerNotFoundException;
import org.burgas.imageservice.handler.RestClientHandler;

import java.io.IOException;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.burgas.imageservice.entity.ImageMessage.NOT_AUTHORIZED_NOT_AUTHENTICATED;
import static org.burgas.imageservice.entity.ImageMessage.PRODUCER_NOT_FOUND;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@WebFilter(
        urlPatterns = {
                "/images/producer/upload-banner",
                "/images/producer/change-banner",
                "/images/producer/delete-banner"
        }
)
public class ProducerBannerFilter implements Filter {

    private final RestClientHandler restClientHandler;

    public ProducerBannerFilter(RestClientHandler restClientHandler) {
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
                identityPrincipal != null &&
                identityPrincipal.getAuthenticated() &&
                identityPrincipal.getId().equals(identityId) &&
                identityPrincipal.getAuthority().equals("ROLE_PRODUCER")
        ) {
            ProducerResponse producerResponse = restClientHandler
                    .getProducerByIdentityId(identityPrincipal.getId(), authentication);
            if (producerResponse != null) {
                filterChain.doFilter(servletRequest, servletResponse);

            } else {
                throw new ProducerNotFoundException(PRODUCER_NOT_FOUND.getMessage());
            }

        } else {
            throw new IdentityNotAuthorizedException(NOT_AUTHORIZED_NOT_AUTHENTICATED.getMessage());
        }
    }
}
