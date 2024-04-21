package com.faol.security.auth;

public class TokenJwtConfig {

    //constantes usadas solo en las clases JwtValidationFilter y JwtAuthFilter:
    public final static String SECRET_KEY =  "token_creado_por_usuario";
    public final static String PREFIX_TOKEN =  "Bearer ";
    public final static String HEADER_AUTH =  "Authorization";
    public final static String CONTENT_TYPE =  "application/json";


}
