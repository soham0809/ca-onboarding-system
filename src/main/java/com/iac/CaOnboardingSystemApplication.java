package com.iac;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(exclude = {org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration.class})
public class CaOnboardingSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(CaOnboardingSystemApplication.class, args);
    }

}