package org.example.notifierservice;

import org.springframework.boot.SpringApplication;

public class TestNotifierServiceApplication {

    public static void main(String[] args) {
        SpringApplication.from(NotifierServiceApplication::main)
                .with(TestcontainersConfiguration.class)
                .run(args);
    }
}
