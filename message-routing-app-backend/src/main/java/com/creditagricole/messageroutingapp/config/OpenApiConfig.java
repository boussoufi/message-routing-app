package com.creditagricole.messageroutingapp.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Message Routing API")
                        .version("1.0")
                        .description("API pour le routage des messages bancaires et la gestion des partenaires")
                        .contact(new Contact()
                                .name("Crédit Agricole CID")
                                .email("support@ca-cid.fr"))
                        .license(new License()
                                .name("Propriétaire")
                                .url("https://www.ca-cib.fr/fr")));
    }
}
