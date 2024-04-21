package com.faol.security.auth.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import static com.faol.security.auth.TokenJwtConfig.*;

import java.io.IOException;
import java.util.*;

public class JwtValidationFilter extends BasicAuthenticationFilter {

    //1) crear constructor:
    public JwtValidationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    //2)generate, override methods, doFilterInternal
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        //3) quitamos contenido por defecto dejando el metodo vacio

        //4)logica de este metodo:
        String header = request.getHeader(HEADER_AUTH);

        if (header == null || !header.startsWith(PREFIX_TOKEN)){
            chain.doFilter(request, response);
            return;
        }

        String token = header.replace(PREFIX_TOKEN, "");
        byte[] tokenDecodedByte = Base64.getDecoder().decode(token);//convierte a bytes
        String tokenDecodedString = new String(tokenDecodedByte);//convierte a String

        //con el punto debe ir doble backslash \\ debido a que el punto es un caracter reservado
        //en una expresion regular que representa cualquier caracter.
        //String[] tokenArr = tokenDecodedString.split("\\.");//convierte a array de String y separa donde encuentra un punto.
        String[] tokenArr = tokenDecodedString.split(":");//convierte a array de String y separa donde encuentra un punto.
        String secretPhrase = tokenArr[0];
        String username = tokenArr[1];

        if (SECRET_KEY.equals(secretPhrase)){

            //aca se necesitan roles y username
            List<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, null, authorities);
            SecurityContextHolder.getContext().setAuthentication(authenticationToken); //autenticacion
            chain.doFilter(request,response);

        }else{

            Map<String, String> body = new HashMap<>();//string string porque solo va a pasar un mensaje de error
            body.put("message", "Invalid jwt token");
            response.getWriter().write(new ObjectMapper().writeValueAsString(body));
            response.setStatus(403);
            response.setContentType(CONTENT_TYPE);

        }

    }
}
