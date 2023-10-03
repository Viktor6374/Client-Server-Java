package com.example.externalinterfaces;

import com.example.externalinterfaces.Configuration.RabbitConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(RabbitConfiguration.class)
public class ExternalInterfacesApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExternalInterfacesApplication.class, args);
    }

}
