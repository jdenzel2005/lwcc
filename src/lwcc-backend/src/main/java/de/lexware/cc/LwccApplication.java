package de.lexware.cc;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
    info = @Info(
        title = "LWCC",
        version = "1.0",
        description = "Lexware Coding Challenge"
    )
)
public class LwccApplication {

    public static void main(String[] args) {
        SpringApplication.run(LwccApplication.class, args);
    }
}
