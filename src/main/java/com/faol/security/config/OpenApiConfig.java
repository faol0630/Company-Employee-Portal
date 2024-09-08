package com.faol.security.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Esta información se verá reflejada en la url de swagger:
 * http://localhost:8080/swagger-ui/index.html#/
 */

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI openAPI(){

        return new OpenAPI()
                .info(new Info()
                        .title("Company Employee Portal")
                        .version("0.0.1")
                        .description("Este proyecto es una aplicación Spring Boot que implementa " +
                                "seguridad con JWT, cuenta con una base de datos relacional conectada " +
                                "mediante JPA y Hibernate. Utiliza el patrón DTO para la transferencia " +
                                "de datos, manejo de excepciones personalizado, y emplea Lombok para " +
                                "reducir la boilerplate. La API está documentada con Swagger, y se han " +
                                "desarrollado pruebas unitarias y de integración con JUnit5 y Mockito. " +
                                "La aplicación interactúa con una base de datos SQL y gestiona entidades " +
                                "relacionadas, como empleados, compañías y departamentos.")


                );
    }
}
