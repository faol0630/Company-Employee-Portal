package com.faol.security.config;

import com.faol.security.dto.CompanyDTO;
import com.faol.security.entity.Department;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CompanyDTOSerializer extends JsonSerializer<CompanyDTO> {

    @Override
    public void serialize(CompanyDTO companyDTO, JsonGenerator gen, SerializerProvider serializers) throws IOException {

        List<Department> departmentList = companyDTO.getDepartmentList();

        List<String> names = new ArrayList<>();

        for (Department department : departmentList) {
            names.add(department.getDeparment_name());
        }

        gen.writeStartObject();
        gen.writeStringField("companyDTO_name", companyDTO.getCompanyName());
        gen.writeStringField("companyDTO_country", companyDTO.getCountry());
        gen.writeStringField("companyDTO_address", companyDTO.getAddress().getStreet() + " " + companyDTO.getAddress().getNumber());
        gen.writeStringField("companyDTO_departmentList", names.toString());
        gen.writeEndObject();
    }
}


