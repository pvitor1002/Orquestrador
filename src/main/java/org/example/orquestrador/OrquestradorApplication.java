package org.example.orquestrador;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class OrquestradorApplication {
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(OrquestradorApplication.class);
        app.run(args);
    }
}
