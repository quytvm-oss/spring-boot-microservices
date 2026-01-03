package org.example.notifierservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class NotifierServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(NotifierServiceApplication.class, args);
    }
}
