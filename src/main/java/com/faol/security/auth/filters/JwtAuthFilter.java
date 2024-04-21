package com.faol.security.auth.filters;

import com.faol.security.entity.Employee;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class JwtAuthFilter extends UsernamePasswordAuthenticationFilter {

    //2)
    private AuthenticationManager authenticationManager;

    //3) constructor:
    public JwtAuthFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    //1) click derecho, generate, override, generar los 3 metodos siguientes:
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        //vaciar el contenido original

        //4) logica de este metodo:
        Employee employee = null;
        String username = null;
        String password = null;

        try {
            employee = new ObjectMapper().readValue(request.getInputStream(), Employee.class);
            username = employee.getUsername();
            password = employee.getPassword();

            logger.info("Username desde request InputStream (raw) " + username);
            logger.info("Password desde request InputStream (raw) " + password);

        }catch (StreamReadException e){
            e.printStackTrace();
        }catch (DatabindException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password);
        return authenticationManager.authenticate(authToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        //vaciar el contenido original

        //5) logica de este metodo:
        String username = ((User) authResult.getPrincipal()).getUsername();
        //token provisional(será reemplazado por un token JWT):
        String originalInput = "token_creado_por_usuario." + username;
        String token = Base64.getEncoder().encodeToString(originalInput.getBytes());

        response.addHeader("Authorization", "Bearer " + token);

        Map<String, Object> body = new HashMap<>();
        body.put("token", token);
        body.put("username", username);
        body.put("message", String.format("Hello %s, successful login", username));
        //convertir Map en un Json:
        response.getWriter().write(new ObjectMapper().writeValueAsString(body));
        response.setStatus(200);
        response.setContentType("application/json");
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        //vaciar el contenido original

        //6) logica de este metodo:
        Map<String, Object> body = new HashMap<>();
        body.put("error", failed.getMessage());
        body.put("message", "authentication error. incorrect username or password");
        //convertir Map en un Json:
        response.getWriter().write(new ObjectMapper().writeValueAsString(body));
        response.setStatus(200);
        response.setContentType("application/json");
    }
}
