package com.faol.security.service;

import com.faol.security.entity.Department;
import com.faol.security.exceptions.DataIntegrityViolationException;
import com.faol.security.exceptions.EntityNotFoundException;
import com.faol.security.exceptions.ResourceNotFoundException;
import com.faol.security.repository.DepartmentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DepartmentServiceImpl implements DepartmentServiceInt{

    @Autowired
    DepartmentRepo departmentRepo;

    @Override
    public List<Department> getAllDepartments() {

        List<Department> departmentList = departmentRepo.findAll();

        if (!departmentList.isEmpty()) {
            return departmentList;
        } else {
            throw new RuntimeException("Error retrieving entities");
        }

    }

    @Override
    public Optional<Department> getDepartmentById(Integer department_id) {

        if (department_id == null){
            throw new IllegalArgumentException("department_id cannot be null");
        }

        Optional<Department> department = departmentRepo.findById(department_id);

        if (department.isPresent()) {
            return department;

        } else {
            throw new EntityNotFoundException("department with id " + department_id + " not found");
        }
    }

    @Override
    public void newDepartment(Department department) {

        if (department == null || department.getDeparment_name() == null){
            throw new IllegalArgumentException("Invalid data provided");
        }

        try {
            departmentRepo.save(department);
        }catch (DataIntegrityViolationException e){
            throw new DataIntegrityViolationException("Data integrity violation while creating entity");
        }

    }

    @Override
    public Department updateDepartment(Department department, Integer department_id) {

        departmentRepo.findById(department_id)
                .orElseThrow( () ->  new EntityNotFoundException("department with id " + department_id + " not found.Nothing to update"));

        if (department == null || department.getDeparment_name() == null){
            throw new IllegalArgumentException("Invalid data provided");
        }

        try{

            Department updatedDepartment = Department.builder()
                    .department_id(department.getDepartment_id())
                    .deparment_name(department.getDeparment_name())
                    .employeeList(department.getEmployeeList())
                    .company(department.getCompany())
                    .build();

            departmentRepo.save(updatedDepartment);

            return updatedDepartment;

        } catch (DataIntegrityViolationException e){

            throw new DataIntegrityViolationException("Data integrity violation while updating entity");

        }

    }

    @Override
    public void deleteDepartmentById(Integer department_id) {

        if (department_id == null){
            throw new IllegalArgumentException("department_id cannot be null");
        }

        if (!departmentRepo.existsById(department_id)){
            throw new EntityNotFoundException("department with id " + department_id + " not found.Nothing to delete");
        }

        Optional<Department> department = departmentRepo.findById(department_id);

        if (department.isPresent()) {
            departmentRepo.deleteById(department_id);
        } else {
            throw new DataIntegrityViolationException("Data integrity violation while deleting entity");
        }

    }

    @Override
    public void deleteAllDepartments() {

        List<Department> departmentList = departmentRepo.findAll();

        if (departmentList.isEmpty()) {
            throw new ResourceNotFoundException("Error deleting entities list");
        } else {
            departmentRepo.deleteAll();

        }

    }
}
