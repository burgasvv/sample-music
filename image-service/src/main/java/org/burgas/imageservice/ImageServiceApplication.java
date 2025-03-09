package org.burgas.imageservice;

import org.burgas.imageservice.filter.IdentityImageFilter;
import org.burgas.imageservice.filter.LabelBannerFilter;
import org.burgas.imageservice.filter.ProducerBannerFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestClient;

@SpringBootApplication
@ServletComponentScan(
        basePackageClasses = {
                IdentityImageFilter.class,
                LabelBannerFilter.class,
                ProducerBannerFilter.class
        }
)
public class ImageServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ImageServiceApplication.class, args);
    }

    @Bean
    public RestClient restClient() {
        return RestClient.builder().build();
    }
}
