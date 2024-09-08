package com.faol.security.service;

import com.faol.security.dto.CompanyDTO;
import com.faol.security.dto.mapper.CompanyDTOMapper;
import com.faol.security.entity.Company;
import com.faol.security.exceptions.IllegalArgumentException;
import com.faol.security.exceptions.*;
import com.faol.security.repository.CompanyRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service con la logica de negocios del entity Company
 */

@Service
public class CompanyServiceImpl implements CompanyServiceInt {

    @Autowired
    CompanyRepo companyRepo;

    /**
     * Metodo que genera una lista de CompanyDTO
     * @return una list de CompanyDTO o una excepcion del tipo ResourceNotFoundException
     * @throws ResourceNotFoundException si la lista de Companies está vacia
     *
     * @see HandlerExceptionController#handleResourceNotFoundException(ResourceNotFoundException)
     */
    @Override
    public List<CompanyDTO> getAllCompanies() {

        List<Company> companyList = companyRepo.findAll();

        if (!companyList.isEmpty()) {
            return companyList.stream().map(company -> CompanyDTOMapper
                            .getInstance()
                            .setCompanyDTOMapper(company)
                            .build())
                    .collect(Collectors.toList()); //genera una lista de CompanyDTO
        } else {
            throw new ResourceNotFoundException("Error retrieving entities");
        }
    }

    /**
     * Metodo que verifica si el parametro id no es nulo o el Optional de Company
     * no esta vacio y retorna un Optional de CompanyDTO
     * @param company_id con el que se va a buscar el Entity
     * @return un Optional de CompanyDTO o una excepcion del tipo EntityNotFoundException
     * @throws EntityNotFoundException si no se encuentra la compañía con el ID especificado
     * @throws IllegalArgumentException si el ID es nulo
     *
     * @see HandlerExceptionController#entityNotFoundException(EntityNotFoundException)
     * @see HandlerExceptionController#illegalArgumentException(IllegalArgumentException)
     */
    @Override
    public Optional<CompanyDTO> getCompanyById(Integer company_id) {

        if (company_id == null){
            throw new IllegalArgumentException("company_id cannot be null");
        }

        Optional<Company> company = companyRepo.findById(company_id);

        if (company.isPresent()) {
            return Optional
                    .of(CompanyDTOMapper.getInstance()
                            .setCompanyDTOMapper(company.orElseThrow())
                            .build());


        } else {
            throw new EntityNotFoundException("Company with id " + company_id + " not found");
        }
    }

    /**
     * Metodo que verifica que los atributos del entity a crear no sean nulos o esten vacios
     * creando un entity Company.Si se presenta un error lanza la excepcion
     * DataIntegrityViolationException
     * @param company es el Entity que va a ser agregado a la lista de Companies ya existente
     * @throws IllegalArgumentException si algun atributo es nulo
     * @throws DataIntegrityViolationException si hay alguna irregularidad con el nuevo Entity
     *
     * @see HandlerExceptionController#illegalArgumentException(IllegalArgumentException)
     * @see HandlerExceptionController#dataIntegrityViolationException(DataIntegrityViolationException)
     */
    @Override
    public void newCompany(Company company) {

        if (company == null || company.getCompany_name() == null){
            throw new IllegalArgumentException("Invalid data provided");
        }

        try {
            companyRepo.save(company);
        }catch (DataIntegrityViolationException e){
            throw new DataIntegrityViolationException("Data integrity violation while creating entity");
        }

    }

    /**
     * Metodo que actualiza un entity Company ya existente.Primero verifica si existe el entity y
     * despues verifica que los atributos de dicho entity no sean nulos.Si se presenta algun error
     * lanza la excepcion DataIntegrityViolationEntity
     * @param company es el Entity con todos los atributos los cuales van a ser modificados
     * @param id_company es con lo que se va a identificar el entity que se quiere modificar
     * @return un entity Company modificado
     * @throws EntityNotFoundException si no se encuentra el Entity a modificar
     * @throws IllegalArgumentException si alguno de los atributos es nulo
     * @throws DataIntegrityViolationException si hay alguna irregularidad mientras se actualiza el entity
     *
     * @see HandlerExceptionController#entityNotFoundException(EntityNotFoundException)
     * @see HandlerExceptionController#illegalArgumentException(IllegalArgumentException)
     * @see HandlerExceptionController#dataIntegrityViolationException(DataIntegrityViolationException)
     */
    @Override
    public CompanyDTO updateCompany(Company company, Integer id_company) {

        companyRepo.findById(id_company)
                .orElseThrow( () ->  new EntityNotFoundException("Company with id " + id_company + " not found.Nothing to update"));

        if (company == null || company.getCompany_name() == null){
            throw new IllegalArgumentException("Invalid data provided");
        }

        try{

            Company updatedCompany = Company.builder()
                    .company_id(id_company)
                    .company_name(company.getCompany_name())
                    .country(company.getCountry())
                    .address(company.getAddress())
                    .departmentList(company.getDepartmentList())
                    .build();

            Company companyToSave = companyRepo.save(updatedCompany);

            return CompanyDTOMapper.getInstance().setCompanyDTOMapper(companyToSave).build();

        } catch (DataIntegrityViolationException e){

            throw new DataIntegrityViolationException("Data integrity violation while updating entity");

        }


    }

    /**
     * Metodo que elimina un entity mediante un id pasado como argumento.Si se presenta
     * algun error lanza la excepcion IllegalArgumentException o la excepcion
     * EntityNotFoundException
     * @param company_id es el identificador con el que se va a buscar el entity que se quiere eliminar
     * @throws EntityNotFoundException si no se encuentra el Entity a eliminar
     * @throws IllegalArgumentException si alguno de los atributos es nulo
     * @throws DataIntegrityViolationException si hay alguna irregularidad mientras se elimina el entity
     *
     * @see HandlerExceptionController#entityNotFoundException(EntityNotFoundException)
     * @see HandlerExceptionController#illegalArgumentException(IllegalArgumentException)
     * @see HandlerExceptionController#dataIntegrityViolationException(DataIntegrityViolationException)
     */
    @Override
    public void deleteCompanyById(Integer company_id) {

        if (company_id == null){
            throw new IllegalArgumentException("company_id cannot be null");
        }

        if (!companyRepo.existsById(company_id)){
            throw new EntityNotFoundException("Company with id " + company_id + " not found.Nothing to delete");
        }

        Optional<Company> company = companyRepo.findById(company_id);

        if (company.isPresent()) {
            companyRepo.deleteById(company_id);
        } else {
            throw new DataIntegrityViolationException("Data integrity violation while deleting entity");
        }

    }

    /**
     * Metodo que elimina todos los entities Company.Si se presenta algun error lanza la
     * excepcion ResourceNotFoundException
     * @throws ResourceNotFoundException si la lista de entities a eliminar
     *
     * @see HandlerExceptionController#handleResourceNotFoundException(ResourceNotFoundException)
     */
    @Override
    public void deleteAllCompanies() {

        List<Company> companyList = companyRepo.findAll();

        if (companyList.isEmpty()) {
            throw new ResourceNotFoundException("Error deleting entities list");
        } else {
            companyRepo.deleteAll();

        }

    }
}
