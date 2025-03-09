package org.burgas.soundservice;

import org.burgas.soundservice.filter.ChangingPackFilter;
import org.burgas.soundservice.filter.ChangingSampleFilter;
import org.burgas.soundservice.filter.UploadSampleFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestClient;

@ServletComponentScan(
        basePackageClasses = {
                ChangingPackFilter.class,
                ChangingSampleFilter.class,
                UploadSampleFilter.class
        }
)
@SpringBootApplication
public class SoundServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SoundServiceApplication.class, args);
    }

    @Bean
    public RestClient restClient() {
        return RestClient.builder().build();
    }
}
