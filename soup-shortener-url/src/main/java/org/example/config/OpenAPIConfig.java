package org.example.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
        info = @Info(
                title = "Soup URL API",
                description = "Shortener URL",
                version = "1.0.0",
                contact = @Contact(
                        name = "Utaliev Sultan",
                        email = "kiziyepi8@gmail.com"
                )
        )
)
public class OpenAPIConfig {
}
