package com.faol.security.auth;

import com.faol.security.entity.Employee;
import com.faol.security.repository.EmployeeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

// 2)
/**
 * Clase que carga los detalles del usuario a partir del nombre de usuario.
 * <p>
 * Busca el usuario (empleado) en la base de datos usando el repositorio de empleados.
 * Si el usuario no se encuentra, se lanza una excepción {@link UsernameNotFoundException}.
 * Si el usuario se encuentra, se extraen sus roles y se convierten en una lista de
 * autoridades {@link GrantedAuthority} que se pasan al objeto {@link User}.
 * </p>
 *
 * <p>
 *     Esta clase se crea justo después de crear SpringSecurityConfig
 * </p>
 *
 * @param username el nombre de usuario del usuario a cargar.
 * @return un objeto {@link UserDetails} que contiene la información del usuario.
 * @throws UsernameNotFoundException si no se encuentra un usuario con el nombre de usuario proporcionado.
 */

@Service
public class JpaUserDetailsService implements UserDetailsService {

    @Autowired
    private EmployeeRepo employeeRepo;


    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //vaciar el contenido original

        //lógica de este método:

        Optional<Employee> employeeOptional = employeeRepo.findByUsername(username);

        if (employeeOptional.isEmpty()){
            throw new UsernameNotFoundException(String.format("Username %s not found", username));
        }

        Employee employee = employeeOptional.orElseThrow();

        /*List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));*/

        //a cada employee le pasamos su lista de roles desde DB:
        List<GrantedAuthority> authorities = employee
                .getRoles()
                .stream()
                .map( role ->
                    new SimpleGrantedAuthority(role.getName())
                ).collect(Collectors.toList());

        return new User(//este User viene con spring framework. No es un entity que hayamos creado.
                employee.getUsername(),
                employee.getPassword(),
                true,
                true,
                true,
                true,
                authorities
        );

    }
}
