package com.faol.security.auth;

import com.faol.security.auth.filters.JwtAuthFilter;
import com.faol.security.auth.filters.JwtValidationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

//1)
/**
 * Configuración de seguridad para la aplicación.
 * <p>
 * Esta clase configura la autenticación y autorización usando JWT (JSON Web Token).
 * Proporciona configuraciones de seguridad como la codificación de contraseñas, la gestión de autenticación
 * y la configuración de filtros de seguridad. En la implementación de seguridad, esta clase se crea primero
 * <p>
 * La configuración incluye:
 * <ul>
 *   <li>Definición de los codificadores de contraseñas</li>
 *   <li>Configuración del `AuthenticationManager`</li>
 *   <li>Configuración de las políticas de autorización y de los filtros de seguridad</li>
 * </ul>
 */
@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {

    @Autowired
    private AuthenticationConfiguration authenticationConfiguration;

    //2)
    /**
     * Define el codificador de contraseñas a usar en la aplicación.
     *
     * @return un <code>BCryptPasswordEncoder</code> que se utilizará para codificar contraseñas.
     */
    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    //3)
    /**
     * Configura el <code>AuthenticationManager</code> utilizado para gestionar la autenticación.
     *
     * @return el <code>AuthenticationManager</code> configurado.
     * @throws Exception si hay un problema al configurar el <code>AuthenticationManager</code>.
     */
    @Bean
    AuthenticationManager authenticationManager() throws Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }

    //1)
    /**
     * Configura la cadena de filtros de seguridad.
     * <p>
     * Define las políticas de autorización para diferentes rutas y métodos HTTP, incluyendo:
     * </p>
     * <ul>
     *   <li>Rutas públicas accesibles sin autenticación.</li>
     *   <li>Rutas accesibles solo para usuarios con roles específicos.</li>
     *   <li>Incorporación de filtros de autenticación y validación JWT.</li>
     *   <li>Deshabilitación de CSRF y configuración de la política de creación de sesión como STATELESS.</li>
     * </ul>
     *
     * @param httpSecurity el objeto <code>HttpSecurity</code> utilizado para configurar la seguridad HTTP.
     * @return la cadena de filtros de seguridad configurada.
     * @throws Exception si hay un problema al configurar la cadena de filtros de seguridad.
     */
    @Bean
    SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity.authorizeHttpRequests(authorizeRequest -> {

            //los roles aca van sin el prefijo ROLE_
                            authorizeRequest
                                    .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
                                    .requestMatchers(HttpMethod.GET, "/employee/get_all").permitAll()
                                    .requestMatchers(HttpMethod.GET, "/company/get_all").permitAll()
                                    .requestMatchers(HttpMethod.GET, "/department/get_all").permitAll()
                                    .requestMatchers(HttpMethod.GET, "/employee/get_employee/{id}").hasAnyRole("USER", "ADMIN")// para varios roles
                                    .requestMatchers(HttpMethod.GET, "/company/get_company/{id}").hasAnyRole("USER", "ADMIN")// para varios roles
                                    .requestMatchers(HttpMethod.GET, "/department/get_department/{id}").hasAnyRole("USER", "ADMIN")// para varios roles
                                    .requestMatchers(HttpMethod.POST, "/employee/new").hasRole("ADMIN")//para un role únicamente
                                    .requestMatchers(HttpMethod.POST, "/company/new").hasRole("ADMIN")//para un role únicamente
                                    .requestMatchers(HttpMethod.POST, "/department/new").hasRole("ADMIN")//para un role únicamente
                                    .requestMatchers("/employee/**").hasRole("ADMIN")//cualquier otra ruta
                                    .requestMatchers("/company/**").hasRole("ADMIN")//cualquier otra ruta
                                    .requestMatchers("/department/**").hasRole("ADMIN")//cualquier otra ruta
                                    .anyRequest().authenticated();
                                    //.anyRequest().permitAll(); //para hacer pruebas
                        }
                )
                //después de haber creado JwtAuthFilter:
                .addFilter(new JwtAuthFilter(authenticationConfiguration.getAuthenticationManager()))
                //después de haber creado JwtValidationFilter:
                .addFilter(new JwtValidationFilter(authenticationConfiguration.getAuthenticationManager()))
                .csrf(config -> config.disable())
                .sessionManagement(managment -> managment.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return httpSecurity.build();

    }


}
