package com.faol.security.auth;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;

public class TokenJwtConfig {

    //constantes usadas solo en las clases JwtValidationFilter y JwtAuthFilter:
    public final static Key SECRET_KEY =  Keys.secretKeyFor(SignatureAlgorithm.HS256);
    public final static String PREFIX_TOKEN =  "Bearer ";
    public final static String HEADER_AUTH =  "Authorization";
    public final static String CONTENT_TYPE =  "application/json";

}
