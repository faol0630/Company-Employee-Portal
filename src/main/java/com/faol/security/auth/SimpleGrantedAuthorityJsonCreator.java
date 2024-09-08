package com.faol.security.auth;

import com.faol.security.auth.filters.JwtValidationFilter;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

//4)
/**
 * Clase utilizada para la deserialización de objetos de autoridad simple (roles) en el contexto de seguridad.
 * <p>
 * Esta clase se debe crear antes de {@link JwtValidationFilter} para asegurar que las autoridades (roles)
 * se puedan deserializar correctamente desde JSON usando Jackson.
 * </p>
 *
 * <p>
 * La anotación {@link JsonCreator} se utiliza para indicar que el constructor debe ser utilizado para crear
 * instancias de la clase a partir de datos JSON. La anotación {@link JsonProperty} especifica el nombre de
 * la propiedad JSON que se debe mapear al parámetro del constructor.
 * </p>
 */

public abstract class SimpleGrantedAuthorityJsonCreator {

    /**
     * Constructor que se utiliza para crear una instancia de {@code SimpleGrantedAuthorityJsonCreator}
     * a partir de un JSON que contiene la propiedad "authority".
     *
     * @param role el nombre del rol o autoridad que se asignará a esta instancia.
     */
    @JsonCreator
    public SimpleGrantedAuthorityJsonCreator(@JsonProperty("authority") String role) {
    }
}
