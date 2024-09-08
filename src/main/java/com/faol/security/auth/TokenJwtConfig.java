package com.faol.security.auth;

import com.faol.security.auth.filters.JwtAuthFilter;
import com.faol.security.auth.filters.JwtValidationFilter;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;


// 3)
/**
 * Clase de configuración para constantes relacionadas con JWT (JSON Web Token).
 * <p>
 * Esta clase debe ser creada antes de {@link JwtValidationFilter} y {@link JwtAuthFilter}.
 * Proporciona constantes que se utilizan para la generación, validación y manejo de JWTs
 * en la aplicación de seguridad.
 * </p>
 */

public class TokenJwtConfig {

    //constantes usadas solo en las clases JwtValidationFilter y JwtAuthFilter:

    /**
     * Clave secreta utilizada para firmar los JWTs.
     * <p>
     * Se genera usando el algoritmo HS256. Esta clave es crucial para la seguridad de los tokens,
     * asegurando que solo los emisores autorizados puedan crear tokens válidos.
     * </p>
     */
    public final static Key SECRET_KEY =  Keys.secretKeyFor(SignatureAlgorithm.HS256);

    /**
     * Prefijo del token JWT.
     * <p>
     * Este prefijo se agrega al token JWT en el encabezado de autorización para indicar que
     * el esquema de autorización es "Bearer".
     * </p>
     */
    public final static String PREFIX_TOKEN =  "Bearer ";

    /**
     * Nombre del encabezado HTTP utilizado para pasar el token JWT.
     * <p>
     * Este es el nombre del encabezado HTTP donde se espera que el token JWT sea enviado.
     * </p>
     */
    public final static String HEADER_AUTH =  "Authorization";

    /**
     * Tipo de contenido para las respuestas HTTP.
     * <p>
     * Se utiliza para especificar que las respuestas HTTP tienen un tipo de contenido JSON.
     * </p>
     */
    public final static String CONTENT_TYPE =  "application/json";

}
