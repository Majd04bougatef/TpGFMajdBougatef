package tn.esprit.TpGFMajdBougatef.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Gestion Foyer API",
                version = "1.0",
                description = "API de gestion de foyer universitaire (CRUD + services avancés)",
                contact = @Contact(name = "Equipe Foyer", email = "contact@foyer.example"),
                license = @License(name = "Apache 2.0")
        )
)
public class OpenApiConfig {
    // Additional grouped OpenAPI configuration can be added here later.
}
