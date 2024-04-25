package com.faol.security.auth.filters;

import com.faol.security.entity.Employee;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.hibernate.sql.ast.tree.expression.Collation;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.faol.security.auth.TokenJwtConfig.*;

public class JwtAuthFilter extends UsernamePasswordAuthenticationFilter {

    //2)
    private final AuthenticationManager authenticationManager;

    //3) constructor:
    public JwtAuthFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    //1) click derecho, generate, override, generar los 3 metodos siguientes y dejarlos sin contenido:
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

            //para ver en consola.En produccion comentar o eliminar ya que muestra password sin encriptar.
            logger.info("Username desde request InputStream (raw) " + username);
            logger.info("Password desde request InputStream (raw) " + password);

        } catch (StreamReadException e) {
            e.printStackTrace();
        } catch (DatabindException e) {
            e.printStackTrace();
        } catch (IOException e) {
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


        //JWT token:
        String token = Jwts.builder()
                .setSubject(username)
                .signWith(SECRET_KEY)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 3600000))
                .compact();

        response.addHeader(HEADER_AUTH, PREFIX_TOKEN + token);

        Map<String, Object> body = new HashMap<>();
        body.put("token", token);
        body.put("username", username);
        body.put("message", String.format("Hello %s, successful login", username));
        //convertir Map en un Json:
        response.getWriter().write(new ObjectMapper().writeValueAsString(body));
        response.setStatus(200);
        response.setContentType(CONTENT_TYPE);
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
        response.setContentType(CONTENT_TYPE);
    }
}
