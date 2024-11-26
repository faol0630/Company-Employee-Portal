package com.faol.security.config;

import com.faol.security.dto.EmployeeDTO;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class EmployeeDTOSerializer extends JsonSerializer<EmployeeDTO> {

    @Override
    public void serialize(EmployeeDTO employeeDTO, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
        gen.writeNumberField("employeeDTO_id", employeeDTO.getId_employee());
        gen.writeStringField("employeeDTO_name", employeeDTO.getName());
        gen.writeStringField("employeeDTO_lastname", employeeDTO.getLastname());
        gen.writeStringField("employeeDTO_email", employeeDTO.getEmail());
        gen.writeStringField("employeeDTO_username", employeeDTO.getUsername());
        gen.writeStringField("employeeDTO_address", employeeDTO.getAddress().getStreet() + " " + employeeDTO.getAddress().getNumber());
        gen.writeStringField("employeeDTO_department", employeeDTO.getDepartment().getDeparment_name());
        gen.writeEndObject();
    }
}

