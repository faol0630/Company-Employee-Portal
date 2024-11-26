package com.faol.security.config;

import com.faol.security.entity.Department;
import com.faol.security.entity.Employee;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DepartmentSerializer extends JsonSerializer<Department> {

    @Override
    public void serialize(Department department, JsonGenerator gen, SerializerProvider serializers) throws IOException {

        List<Employee> employeeList = department.getEmployeeList();

        List<String> names = new ArrayList<>();

        for (Employee employee : employeeList) {
            names.add(employee.getName() + " " + employee.getLastname());
        }

        gen.writeStartObject();
        gen.writeNumberField("department_id", department.getDepartment_id());
        gen.writeStringField("department_name", department.getDeparment_name());
        gen.writeStringField("company", department.getCompany().getCompany_name());
        gen.writeStringField("employeeList", names.toString());
        gen.writeEndObject();
    }
}
