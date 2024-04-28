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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class JpaUserDetailsService implements UserDetailsService {

    @Autowired
    private EmployeeRepo employeeRepo;


    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //vaciar el contenido original

        //logica de este metodo:

        Optional<Employee> employeeOptional = employeeRepo.findByUsername(username);

        if (!employeeOptional.isPresent()){
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

        return new User(//este User viene con spring framework.No es un entity que hayamos creado.
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
