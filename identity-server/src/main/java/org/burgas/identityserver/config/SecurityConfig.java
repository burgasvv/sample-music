package org.burgas.identityserver.config;

import org.burgas.identityserver.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;
    private final PasswordEncoder passwordEncoder;

    public SecurityConfig(
            CustomUserDetailsService customUserDetailsService, PasswordEncoder passwordEncoder
    ) {
        this.customUserDetailsService = customUserDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public SecurityFilterChain securityWebFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .cors(withDefaults())
                .httpBasic(withDefaults())
                .authenticationManager(authenticationManager())
                .authorizeHttpRequests(
                        authorizeExchangeSpec -> authorizeExchangeSpec

                                .requestMatchers(
                                        "/v3/api-docs","/v3/api-docs/**",
                                        "/producer-service/v3/api-docs","/producer-service/v3/api-docs/**",
                                        "/subscription-service/v3/api-docs","/subscription-service/v3/api-docs/**",
                                        "/image-service/v3/api-docs","/image-service/v3/api-docs/**",
                                        "/sound-service/v3/api-docs","/sound-service/v3/api-docs/**",
                                        "/payment-service/v3/api-docs","/payment-service/v3/api-docs/**",

                                        "/swagger-ui.html","/swagger-ui/**",
                                        "/swagger-resources", "/swagger-resources/**",
                                        "/configuration/ui", "/configuration/security",
                                        "/webjars/**",

                                        "/producer-service/swagger-ui.html","/producer-service/swagger-ui/**",
                                        "/producer-service/swagger-resources", "/producer-service/swagger-resources/**",
                                        "/producer-service/configuration/ui", "/producer-service/configuration/security",
                                        "/producer-service/webjars/**",

                                        "/subscription-service/swagger-ui.html","/subscription-service/swagger-ui/**",
                                        "/subscription-service/swagger-resources", "/subscription-service/swagger-resources/**",
                                        "/subscription-service/configuration/ui", "/subscription-service/configuration/security",
                                        "/subscription-service/webjars/**",

                                        "/image-service/swagger-ui.html","/image-service/swagger-ui/**",
                                        "/image-service/swagger-resources", "/image-service/swagger-resources/**",
                                        "/image-service/configuration/ui", "/image-service/configuration/security",
                                        "/image-service/webjars/**",

                                        "/sound-service/swagger-ui.html","/sound-service/swagger-ui/**",
                                        "/sound-service/swagger-resources", "/sound-service/swagger-resources/**",
                                        "/sound-service/configuration/ui", "/sound-service/configuration/security",
                                        "/sound-service/webjars/**",

                                        "/payment-service/swagger-ui.html","/payment-service/swagger-ui/**",
                                        "/payment-service/swagger-resources", "/payment-service/swagger-resources/**",
                                        "/payment-service/configuration/ui", "/payment-service/configuration/security",
                                        "/payment-service/webjars/**"
                                )
                                .permitAll()

                                .requestMatchers(
                                        "/identities/create",
                                        "/identities/by-producer-token/{token}",
                                        "/identities/by-payment-token/{token}", "/authentication/principal",
                                        "/plans/by-type","/plans/by-period-type","/plans/by-id","/images/by-id",
                                        "/packs/all","/packs/by-genre","/packs/by-label",
                                        "/packs/by-id","/packs/by-title","/samples/by-id","/samples/by-pack",
                                        "/discounts/by-id","/discounts/all"
                                )
                                .permitAll()

                                .requestMatchers("/authentication/csrf-token",
                                        "/identities/by-id","/identities/by-username",
                                        "/identities/by-email", "/identities/update","/identities/delete",
                                        "/authorities/all","/authorities/by-id","/authorities/by-name",
                                        "/labels/all","/labels/by-id","/labels/by-title",
                                        "/producers/all","/producers/all/by-labelId","/producers/by-id",
                                        "/producers/by-identity","/producers/create",
                                        "/subscriptions/by-identity","/subscriptions/subscribe",
                                        "/subscriptions/restore-subscription","/subscriptions/cancel-subscription",
                                        "/images/identity/upload-image","/images/identity/change-image","/images/identity/delete-image",
                                        "/samples/add","/payments/by-id"
                                )
                                .hasAnyAuthority("ROLE_ADMIN","ROLE_PRODUCER","ROLE_USER")

                                .requestMatchers(
                                        "/labels/create","/labels/update","/labels/delete",
                                        "/producers/update","/producers/delete",
                                        "/images/label/upload-banner", "/images/label/change-banner","/images/label/delete-banner",
                                        "/images/producer/upload-banner", "/images/producer/change-banner","/images/producer/delete-banner",
                                        "/packs/create","/packs/update","/packs/delete",
                                        "/samples/upload","/samples/update"
                                )
                                .hasAnyAuthority("ROLE_PRODUCER")

                                .requestMatchers(
                                        "/identities/all",
                                        "/authorities/create","/authorities/update","/authorities/delete",
                                        "/plans/create","/plans/update","/plans/delete",
                                        "/subscriptions/by-id","/subscriptions/all",
                                        "/images/upload","/images/change","/images/delete",
                                        "/discounts/create"
                                )
                                .hasAnyAuthority("ROLE_ADMIN")
                )
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider(passwordEncoder);
        daoAuthenticationProvider.setUserDetailsService(customUserDetailsService);
        return new ProviderManager(daoAuthenticationProvider);
    }
}
