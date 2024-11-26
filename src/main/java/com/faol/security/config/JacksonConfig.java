package com.faol.security.config;

import com.faol.security.dto.CompanyDTO;
import com.faol.security.dto.EmployeeDTO;
import com.faol.security.entity.Address;
import com.faol.security.entity.Department;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JacksonConfig {

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();

        // Crear un módulo y registrar el serializador personalizado
        SimpleModule module = new SimpleModule();
        module.addSerializer(Department.class, new DepartmentSerializer());
        module.addSerializer(EmployeeDTO.class, new EmployeeDTOSerializer());
        module.addSerializer(Address.class, new AddressSerializer());
        module.addSerializer(CompanyDTO.class, new CompanyDTOSerializer());

        // Registrar el módulo en el ObjectMapper
        mapper.registerModule(module);

        // Configurar otras opciones si es necesario
        // mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

        return mapper;
    }
}
