package org.burgas.soundservice.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import org.burgas.soundservice.dto.IdentityPrincipal;
import org.burgas.soundservice.exception.IdentityNotAuthorizedException;
import org.burgas.soundservice.handler.RestClientHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import static org.burgas.soundservice.entity.PackMessage.NOT_AUTHORIZED;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@WebFilter(
        urlPatterns = {
                "/samples/update",
                "/samples/add"
        }
)
public class ChangingSampleFilter implements Filter {

    private static final Logger log = LoggerFactory.getLogger(ChangingSampleFilter.class);
    private final RestClientHandler restClientHandler;

    public ChangingSampleFilter(RestClientHandler restClientHandler) {
        this.restClientHandler = restClientHandler;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        String authentication = httpServletRequest.getHeader(AUTHORIZATION);
        IdentityPrincipal identityPrincipal = restClientHandler.getPrincipal(authentication).getBody();

        if (identityPrincipal != null && identityPrincipal.getAuthenticated()) {
            filterChain.doFilter(servletRequest, servletResponse);

        } else {
            log.warn("CHANGING-SAMPLE-FILTER::Identity Not Authorized");
            throw new IdentityNotAuthorizedException(NOT_AUTHORIZED.getMessage());
        }
    }
}
