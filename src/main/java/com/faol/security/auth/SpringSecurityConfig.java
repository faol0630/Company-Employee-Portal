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

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {

    @Autowired
    private AuthenticationConfiguration authenticationConfiguration;

    //2)
    @Bean //solo para probar .sera reemplazado despues
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    //3)
    @Bean
    AuthenticationManager authenticationManager() throws Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }

    //1)
    @Bean
    SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity.authorizeHttpRequests(authorizeRequest -> {

            //los roles aca van sin el prefijo ROLE_
                            authorizeRequest
                                    .requestMatchers(HttpMethod.GET, "/employee/get_all").permitAll()
                                    .requestMatchers(HttpMethod.GET, "/employee/get_employee/{id}").hasAnyRole("USER", "ADMIN")// para varios roles
                                    .requestMatchers(HttpMethod.POST, "/employee/new").hasRole("ADMIN")//para un role unicamente
                                    .requestMatchers("/employee/**").hasRole("ADMIN")//cualquier otra ruta
                                    .anyRequest().authenticated();
                        }
                )
                .addFilter(new JwtAuthFilter(authenticationConfiguration.getAuthenticationManager()))//despues de haber creado JwtAuthFilter
                .addFilter(new JwtValidationFilter(authenticationConfiguration.getAuthenticationManager()))//despues de haber creado JwtValidationFilter
                .csrf(config -> config.disable())
                .sessionManagement(managment -> managment.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return httpSecurity.build();

    }


}
