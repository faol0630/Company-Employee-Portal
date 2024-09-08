package com.faol.security.auth.filters;

import com.faol.security.auth.SimpleGrantedAuthorityJsonCreator;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
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

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static com.faol.security.auth.TokenJwtConfig.*;

/**
 * Filtro de validación de JWT para la aplicación.
 * <p>
 * Esta clase extiende {@link BasicAuthenticationFilter} y se encarga de validar el token JWT presente en el encabezado
 * de la solicitud HTTP. Si el token es válido, extrae las reclamaciones, incluyendo las autoridades y el nombre de usuario,
 * y establece la autenticación en el contexto de seguridad. En caso de un token inválido, proporciona un mensaje de error
 * en la respuesta.
 * </p>
 */
public class JwtValidationFilter extends BasicAuthenticationFilter {

    //1) crear constructor:
    /**
     * Constructor del filtro de validación de JWT.
     *
     * @param authenticationManager el {@link AuthenticationManager} utilizado para la autenticación.
     */
    public JwtValidationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    //2)generate, override methods, doFilterInternal
    /**
     * Realiza la validación del token JWT presente en el encabezado de la solicitud HTTP.
     * <p>
     * Este método verifica la presencia y validez del token JWT en el encabezado de la solicitud. Si el token es válido,
     * extrae el nombre de usuario y las autoridades del token y establece la autenticación en el contexto de seguridad.
     * Si el token es inválido, se devuelve un mensaje de error en la respuesta HTTP con el estado 403 (Forbidden).
     * </p>
     *
     * @param request la solicitud HTTP que puede contener un token JWT en el encabezado.
     * @param response la respuesta HTTP que se utiliza para enviar mensajes de error si el token es inválido.
     * @param chain la cadena de filtros que se utilizará para continuar con la siguiente etapa de la solicitud.
     * @throws IOException si ocurre un error al leer o escribir en la solicitud o respuesta.
     * @throws ServletException si ocurre un error al procesar la solicitud.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        //3) quitamos contenido por defecto dejando el método vacío

        //4)lógica de este método:
        String header = request.getHeader(HEADER_AUTH);

        if (header == null || !header.startsWith(PREFIX_TOKEN)){
            chain.doFilter(request, response);
            return;
        }

        String token = header.replace(PREFIX_TOKEN, "");

        try{

            Claims claims = Jwts.parserBuilder().setSigningKey(SECRET_KEY)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            String username = claims.getSubject();

            /*
            List<GrantedAuthority> authorities = new ArrayList<>();
             authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
            */

            Object authoritiesClaims = claims.get("authorities");

            Collection<? extends GrantedAuthority> authorities = Arrays.asList(new ObjectMapper()
                    .addMixIn(SimpleGrantedAuthority.class, SimpleGrantedAuthorityJsonCreator.class)
                    .readValue(authoritiesClaims.toString().getBytes(), SimpleGrantedAuthority[].class));

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, null, authorities);
            SecurityContextHolder.getContext().setAuthentication(authenticationToken); //autenticación
            chain.doFilter(request,response);

        }catch (JwtException e){

            Map<String, String> body = new HashMap<>();//string, string porque solo va a pasar mensajes de error
            body.put("error", e.getMessage());
            body.put("message", "Invalid jwt token");
            response.getWriter().write(new ObjectMapper().writeValueAsString(body));
            response.setStatus(403);
            response.setContentType(CONTENT_TYPE);

        }

    }
}
