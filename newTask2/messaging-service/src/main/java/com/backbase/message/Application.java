package com.backbase.message;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class Application extends SpringBootServletInitializer {

    public static void main(final String[] args) {
        System.setProperty("SIG_SECRET_KEY", "JWTSecretKeyDontUseInProduction!");
// Retrieve the property
        String sigSecret= System.getProperty("SIG_SECRET_KEY");
// Print the value
        if (sigSecret != null) {
            System.out.println("SIG_SECRET_KEY: " + sigSecret);
        } else {
            System.out.println("SIG_SECRET_KEY is not set.");
        }
        SpringApplication.run(Application.class, args);
    }
}