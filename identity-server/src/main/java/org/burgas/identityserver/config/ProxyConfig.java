package org.burgas.identityserver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.RouterFunctions;
import org.springframework.web.servlet.function.ServerResponse;

import static org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions.http;

@Configuration
public class ProxyConfig {

    private static final String PRODUCER_SERVICE_URL = "http://localhost:9000";
    private static final String SUBSCRIPTION_SERVICE_URL = "http://localhost:9010";
    private static final String IMAGE_SERVICE_URL = "http://localhost:9020";
    private static final String SOUND_SERVICE_URL = "http://localhost:9030";
    private static final String PAYMENT_SERVICE_URL = "http://localhost:9040";

    @Bean
    public RouterFunction<ServerResponse> routes() {
        return RouterFunctions
                .route()

                .GET("/labels/**", http(PRODUCER_SERVICE_URL))
                .POST("/labels/**", http(PRODUCER_SERVICE_URL))
                .PUT("/labels/**", http(PRODUCER_SERVICE_URL))
                .DELETE("/labels/**", http(PRODUCER_SERVICE_URL))
                .GET("/producers/**", http(PRODUCER_SERVICE_URL))
                .POST("/producers/**", http(PRODUCER_SERVICE_URL))
                .PUT("/producers/**", http(PRODUCER_SERVICE_URL))
                .DELETE("/producers/**", http(PRODUCER_SERVICE_URL))
                .GET("/producer-service/v3/api-docs", http(PRODUCER_SERVICE_URL))
                .GET("/producer-service/v3/api-docs/**", http(PRODUCER_SERVICE_URL))

                .GET("/plans/**", http(SUBSCRIPTION_SERVICE_URL))
                .POST("/plans/**", http(SUBSCRIPTION_SERVICE_URL))
                .PUT("/plans/**", http(SUBSCRIPTION_SERVICE_URL))
                .DELETE("/plans/**", http(SUBSCRIPTION_SERVICE_URL))
                .GET("/subscriptions/**", http(SUBSCRIPTION_SERVICE_URL))
                .POST("/subscriptions/**", http(SUBSCRIPTION_SERVICE_URL))
                .PUT("/subscriptions/**", http(SUBSCRIPTION_SERVICE_URL))
                .DELETE("/subscriptions/**", http(SUBSCRIPTION_SERVICE_URL))
                .GET("/discounts/**", http(SUBSCRIPTION_SERVICE_URL))
                .POST("/discounts/**", http(SUBSCRIPTION_SERVICE_URL))
                .PUT("/discounts/**", http(SUBSCRIPTION_SERVICE_URL))
                .DELETE("/discounts/**", http(SUBSCRIPTION_SERVICE_URL))
                .GET("/subscription-service/v3/api-docs", http(SUBSCRIPTION_SERVICE_URL))
                .GET("/subscription-service/v3/api-docs/**", http(SUBSCRIPTION_SERVICE_URL))

                .GET("/images/**", http(IMAGE_SERVICE_URL))
                .POST("/images/**", http(IMAGE_SERVICE_URL))
                .PUT("/images/**", http(IMAGE_SERVICE_URL))
                .DELETE("/images/**", http(IMAGE_SERVICE_URL))
                .GET("/image-service/v3/api-docs", http(IMAGE_SERVICE_URL))
                .GET("/image-service/v3/api-docs/**", http(IMAGE_SERVICE_URL))

                .GET("/packs/**", http(SOUND_SERVICE_URL))
                .POST("/packs/**", http(SOUND_SERVICE_URL))
                .PUT("/packs/**", http(SOUND_SERVICE_URL))
                .DELETE("/packs/**", http(SOUND_SERVICE_URL))
                .GET("/samples/**", http(SOUND_SERVICE_URL))
                .POST("/samples/**", http(SOUND_SERVICE_URL))
                .PUT("/samples/**", http(SOUND_SERVICE_URL))
                .DELETE("/samples/**", http(SOUND_SERVICE_URL))
                .GET("/sound-service/v3/api-docs", http(SOUND_SERVICE_URL))
                .GET("/sound-service/v3/api-docs/**", http(SOUND_SERVICE_URL))

                .GET("/payments/**", http(PAYMENT_SERVICE_URL))
                .POST("/payments/**", http(PAYMENT_SERVICE_URL))
                .PUT("/payments/**", http(PAYMENT_SERVICE_URL))
                .DELETE("/payments/**", http(PAYMENT_SERVICE_URL))
                .GET("/payment-service/v3/api-docs", http(PAYMENT_SERVICE_URL))
                .GET("/payment-service/v3/api-docs/**", http(PAYMENT_SERVICE_URL))

                .build();
    }
}
