package com.faol.security.auth;

import com.faol.security.auth.filters.JwtAuthFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
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
                            authorizeRequest
                                    //.requestMatchers(HttpMethod.POST, "/employee/new")
                                    //.permitAll()
                                    .requestMatchers(HttpMethod.GET, "/employee/get_all")
                                    .permitAll()
                                    .anyRequest().authenticated();
                        }
                )
                .addFilter(new JwtAuthFilter(authenticationConfiguration.getAuthenticationManager()))//despues de haber creado JwtAuthFilter
                .csrf(config -> config.disable())
                .sessionManagement(managment -> managment.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return httpSecurity.build();

    }


}
