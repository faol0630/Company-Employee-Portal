package com.faol.security.auth;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
// 7)
/**
 * Configuración de seguridad para pruebas.
 * <p>
 * Esta clase se utiliza únicamente para los tests de los controladores, proporcionando
 * una configuración de seguridad simplificada que deshabilita la protección CSRF y
 * permite todas las solicitudes sin restricciones.
 * </p>
 */
@TestConfiguration
public class TestSecurityConfig {

    /**
     * Configura una cadena de filtros de seguridad para las pruebas.
     * <p>
     * Esta configuración deshabilita la protección CSRF y permite todas las solicitudes,
     * lo que facilita la realización de pruebas sin las restricciones habituales de seguridad.
     * </p>
     *
     * @param http el objeto {@link HttpSecurity} utilizado para configurar la seguridad HTTP.
     * @return la cadena de filtros de seguridad configurada.
     * @throws Exception si hay un problema al configurar la seguridad HTTP.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())  // Disable CSRF
                .authorizeHttpRequests(authorize -> authorize.anyRequest().permitAll());  // Permit all requests
        return http.build();
    }
}



