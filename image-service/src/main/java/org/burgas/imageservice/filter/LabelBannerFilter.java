package org.burgas.imageservice.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import org.burgas.imageservice.dto.IdentityPrincipal;
import org.burgas.imageservice.dto.LabelResponse;
import org.burgas.imageservice.dto.ProducerResponse;
import org.burgas.imageservice.exception.IdentityNotAuthorizedException;
import org.burgas.imageservice.exception.NotMemberOfLabelException;
import org.burgas.imageservice.exception.NotOwnerOfLabelException;
import org.burgas.imageservice.handler.RestClientHandler;

import java.io.IOException;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.burgas.imageservice.entity.ImageMessage.*;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@WebFilter(
        urlPatterns = {
                "/images/label/upload-banner",
                "/images/label/change-banner",
                "/images/label/delete-banner",
        }
)
public class LabelBannerFilter implements Filter {

    private final RestClientHandler restClientHandler;

    public LabelBannerFilter(RestClientHandler restClientHandler) {
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
        Long labelId = Long.parseLong(
                new String(httpServletRequest.getPart("labelId").getInputStream().readAllBytes(), UTF_8)
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
            if (
                    (producerResponse != null ? producerResponse.getId() : null) != null &&
                    producerResponse.getLabelId().equals(labelId)
            ) {
                LabelResponse labelResponse = restClientHandler
                        .getLabelById(producerResponse.getLabelId(), authentication)
                        .getBody();

                if (labelResponse != null && labelResponse.getBossId().equals(producerResponse.getId())) {
                    filterChain.doFilter(servletRequest, servletResponse);

                } else {
                    throw new NotOwnerOfLabelException(PRODUCER_NOT_OWNER_OF_LABEL.getMessage());
                }

            } else {
                throw new NotMemberOfLabelException(PRODUCER_NOT_A_MEMBER_OF_LABEL.getMessage());
            }

        } else
            throw new IdentityNotAuthorizedException(NOT_AUTHORIZED_NOT_AUTHENTICATED.getMessage());
    }
}
