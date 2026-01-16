package io.ggroup.demo.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("ProjectG API")
                        .version("1.0")
                        .description("REST API documentation for ProjectG - A Spring Boot + React application")
                        .contact(new Contact()
                                .name("ProjectG Team")
                                .email("info@projectg.com")));
    }
}
