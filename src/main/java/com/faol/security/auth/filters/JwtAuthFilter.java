package com.faol.security.auth.filters;

import com.faol.security.entity.Employee;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.faol.security.auth.TokenJwtConfig.*;

/**
 * Filtro de autenticación JWT para la aplicación.
 * <p>
 * Esta clase extiende {@link UsernamePasswordAuthenticationFilter} y se encarga de manejar el proceso de autenticación
 * usando JSON Web Tokens (JWT). El filtro extrae las credenciales del usuario desde el cuerpo de la solicitud, autentica
 * al usuario, y genera un token JWT en caso de éxito. En caso de fallo en la autenticación, se proporciona una respuesta
 * adecuada.
 * </p>
 */
public class JwtAuthFilter extends UsernamePasswordAuthenticationFilter {

    //2)
    private final AuthenticationManager authenticationManager;

    //3) constructor:
    /**
     * Constructor del filtro de autenticación JWT.
     *
     * @param authenticationManager el {@link AuthenticationManager} utilizado para autenticar las credenciales del usuario.
     */
    public JwtAuthFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    //1) clic derecho, genérate, override, generar los 3 métodos siguientes y dejarlos sin contenido:
    /**
     * Intenta autenticar al usuario usando las credenciales proporcionadas en la solicitud.
     * <p>
     * Extrae el nombre de usuario y la contraseña desde el cuerpo de la solicitud JSON y crea un token de autenticación
     * {@link UsernamePasswordAuthenticationToken}. Luego, utiliza el {@link AuthenticationManager} para autenticar el token.
     * </p>
     *
     * @param request la solicitud HTTP que contiene las credenciales del usuario.
     * @param response la respuesta HTTP que puede ser modificada si es necesario.
     * @return el objeto {@link Authentication} que representa al usuario autenticado.
     * @throws AuthenticationException si ocurre un error durante la autenticación.
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        //vaciar el contenido original

        //4) lógica de este método:
        Employee employee;
        String username = null;
        String password = null;

        try {
            employee = new ObjectMapper().readValue(request.getInputStream(), Employee.class);
            username = employee.getUsername();
            password = employee.getPassword();

            /*
            para ver en consola.En producción comentar o eliminar ya que muestra password sin encriptar.
            logger.info("Username desde request InputStream (raw) " + username);
            logger.info("Password desde request InputStream (raw) " + password);
            */

        } catch (StreamReadException | DatabindException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password);
        return authenticationManager.authenticate(authToken);
    }

    /**
     * Maneja una autenticación exitosa y genera un token JWT.
     * <p>
     * Si la autenticación es exitosa, genera un token JWT que incluye las autoridades del usuario y otros datos relevantes.
     * Luego, agrega el token JWT en el encabezado de la respuesta y también incluye el token y el nombre de usuario en el cuerpo de la respuesta.
     * </p>
     *
     * @param request la solicitud HTTP.
     * @param response la respuesta HTTP en la que se incluirá el token JWT.
     * @param chain la cadena de filtros.
     * @param authResult el objeto {@link Authentication} que representa al usuario autenticado.
     * @throws IOException si ocurre un error al escribir en la respuesta.
     * @throws ServletException si ocurre un error al procesar la solicitud.
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        //vaciar el contenido original

        //5) lógica de este método:
        String username = ((User) authResult.getPrincipal()).getUsername();

        Collection<? extends GrantedAuthority> roles = authResult.getAuthorities();
        //para averiguar si es Admin:
        boolean isAdmin = roles.stream().anyMatch(role -> role.getAuthority().equals("ROLE_ADMIN"));
        Claims claims = Jwts.claims(); //data dentro del token
        claims.put("authorities", new ObjectMapper().writeValueAsString(roles));//agregamos los roles s claims
        claims.put("isAdmin", isAdmin);


        //JWT token:
        String token = Jwts.builder()
                .setClaims(claims)//pasando los roles al token usando claims.
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

    /**
     * Maneja una autenticación fallida.
     * <p>
     * Si la autenticación falla, proporciona un mensaje de error en la respuesta para informar al usuario.
     * </p>
     *
     * @param request la solicitud HTTP.
     * @param response la respuesta HTTP en la que se incluirá el mensaje de error.
     * @param failed el objeto {@link AuthenticationException} que representa el error de autenticación.
     * @throws IOException si ocurre un error al escribir en la respuesta.
     * @throws ServletException si ocurre un error al procesar la solicitud.
     */
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        //vaciar el contenido original

        //6) lógica de este método:
        Map<String, Object> body = new HashMap<>();
        body.put("error", failed.getMessage());
        body.put("message", "authentication error. incorrect username or password");
        //convertir Map en un Json:
        response.getWriter().write(new ObjectMapper().writeValueAsString(body));
        response.setStatus(200);
        response.setContentType(CONTENT_TYPE);
    }
}
