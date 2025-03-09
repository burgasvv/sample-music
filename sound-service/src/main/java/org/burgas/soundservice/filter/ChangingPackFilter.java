package org.burgas.soundservice.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import org.burgas.soundservice.dto.IdentityPrincipal;
import org.burgas.soundservice.dto.ProducerResponse;
import org.burgas.soundservice.exception.IdentityNotAuthorizedException;
import org.burgas.soundservice.exception.WrongLabelException;
import org.burgas.soundservice.handler.RestClientHandler;

import java.io.IOException;

import static java.util.Objects.requireNonNull;
import static org.burgas.soundservice.entity.PackMessage.NOT_AUTHORIZED;
import static org.burgas.soundservice.entity.PackMessage.WRONG_LABEL;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@WebFilter(
        urlPatterns = {
                "/packs/create",
                "/packs/update",
                "/packs/delete"
        }
)
public class ChangingPackFilter implements Filter {

    private final RestClientHandler restClientHandler;

    public ChangingPackFilter(RestClientHandler restClientHandler) {
        this.restClientHandler = restClientHandler;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {

        final HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        final String authentication = httpServletRequest.getHeader(AUTHORIZATION);
        String labelId = httpServletRequest.getParameter("labelId");

        final IdentityPrincipal identityPrincipal = restClientHandler.getPrincipal(authentication).getBody();
        if (identityPrincipal != null && identityPrincipal.getAuthenticated()) {

            final ProducerResponse producerResponse = restClientHandler
                    .getProducerByIdentityId(requireNonNull(identityPrincipal).getId(), authentication)
                    .getBody();

            if (requireNonNull(producerResponse).getLabelId().equals(labelId.isEmpty() ? 0L : Long.parseLong(labelId))) {
                filterChain.doFilter(servletRequest, servletResponse);

            } else {
                throw new WrongLabelException(WRONG_LABEL.getMessage());
            }

        } else {
            throw new IdentityNotAuthorizedException(NOT_AUTHORIZED.getMessage());
        }
    }
}
