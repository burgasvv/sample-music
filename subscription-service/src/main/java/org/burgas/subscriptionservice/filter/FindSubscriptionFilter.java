package org.burgas.subscriptionservice.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.burgas.subscriptionservice.dto.IdentityPrincipal;
import org.burgas.subscriptionservice.exception.IdentityNotAuthorizedException;
import org.burgas.subscriptionservice.handler.RestClientHandler;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static java.util.Objects.requireNonNull;
import static org.burgas.subscriptionservice.entity.SubscriptionMessage.IDENTITY_NOT_AUTHORIZED;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@WebFilter(urlPatterns = "/subscriptions/by-id")
public class FindSubscriptionFilter extends OncePerRequestFilter {

    private final RestClientHandler restClientHandler;

    public FindSubscriptionFilter(RestClientHandler restClientHandler) {
        this.restClientHandler = restClientHandler;
    }

    @Override
    protected void doFilterInternal(
            @NotNull HttpServletRequest request,
            @NotNull HttpServletResponse response, @NotNull FilterChain filterChain)
            throws ServletException, IOException {

        String authentication = request.getHeader(AUTHORIZATION);
        String identityId = request.getParameter("identityId");
        IdentityPrincipal identityPrincipal = restClientHandler.getPrincipal(authentication).getBody();

        if (
                requireNonNull(identityPrincipal).getAuthenticated() &&
                identityPrincipal.getId().equals(Long.parseLong(identityId))
        ) {
            filterChain.doFilter(request, response);

        } else {
            throw new IdentityNotAuthorizedException(IDENTITY_NOT_AUTHORIZED.getMessage());
        }
    }
}
