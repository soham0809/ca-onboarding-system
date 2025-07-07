package com.iac.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI caOnboardingOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("CA Onboarding System API")
                .description("API documentation for the CA Onboarding System")
                .version("1.0.0")
                .contact(new Contact()
                    .name("Cloud Counselage")
                    .email("support@cloudcounselage.com")
                    .url("https://www.cloudcounselage.com")));
    }
}