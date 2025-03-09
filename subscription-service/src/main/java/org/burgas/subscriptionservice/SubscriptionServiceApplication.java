package org.burgas.subscriptionservice;

import org.burgas.subscriptionservice.filter.FindSubscriptionFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestClient;

@EnableScheduling
@SpringBootApplication
@ServletComponentScan(
        basePackageClasses = {
                FindSubscriptionFilter.class
        }
)
public class SubscriptionServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SubscriptionServiceApplication.class, args);
	}

    @Bean
    public RestClient webClient() {
        return RestClient.builder().build();
    }
}
