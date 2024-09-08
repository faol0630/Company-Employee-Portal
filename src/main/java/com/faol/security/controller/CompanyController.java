package com.faol.security.controller;

import com.faol.security.dto.CompanyDTO;
import com.faol.security.dto.mapper.CompanyDTOMapper;
import com.faol.security.entity.Company;
import com.faol.security.exceptions.*;
import com.faol.security.exceptions.IllegalArgumentException;
import com.faol.security.service.CompanyServiceInt;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

/**
 * Controller con los Endpoints de la clase Company
 */

@RestController
@RequestMapping("/company")
public class CompanyController {

    @Autowired
    CompanyServiceInt service;

    /**
     * Endpoint que retorna una lista de CompanyDTO
     *
     * @return un mensaje de error o de confirmación, estatus y una lista de CompanyDTO
     * @throws ResourceNotFoundException si la lista de Companies está vacía
     *
     * @see HandlerExceptionController#handleResourceNotFoundException(ResourceNotFoundException)
     */
    @GetMapping("/get_all")
    @SuppressWarnings("unused")
    public ResponseEntity<?> getAllCompanies() {

        try {

            HashMap<String, Object> response = new HashMap<>();
            List<CompanyDTO> result = service.getAllCompanies();

            response.put("message", "List ok");
            response.put("companyDTOList", result);
            return ResponseEntity.status(HttpStatus.OK).body(response);

        } catch (ResourceNotFoundException ex) {
            ResponseEntity<ErrorDetails> errorResponse = HandlerExceptionController.handleResourceNotFoundException(ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }

    }

    /**
     * Endpoint que retorna un CompanyDTO
     *
     * @param id usado para encontrar el Entity correspondiente
     * @return un mensaje de error o de confirmación, estatus y un CompanyDTO
     * @throws EntityNotFoundException  si no se encuentra la compañía con el ID especificado
     * @throws IllegalArgumentException si el ID es nulo
     *
     * @see HandlerExceptionController#entityNotFoundException(EntityNotFoundException)
     * @see HandlerExceptionController#illegalArgumentException(IllegalArgumentException)
     */
    @GetMapping("/get_company/{id}")
    @SuppressWarnings("unused")
    public ResponseEntity<?> getCompanyById(@PathVariable Integer id) {


        try {
            HashMap<String, Object> response = new HashMap<>();
            Optional<CompanyDTO> companyDTO = service.getCompanyById(id);
            response.put("message", "company found");
            response.put("companyDTO", companyDTO.orElseThrow());
            return ResponseEntity.status(HttpStatus.OK).body(response);

        } catch (EntityNotFoundException ex) {

            ResponseEntity<ErrorDetails> errorResponse = HandlerExceptionController.entityNotFoundException(ex);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        } catch (IllegalArgumentException e) {
            ResponseEntity<ErrorDetails> errorResponse = HandlerExceptionController.illegalArgumentException(e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }

    }

    /**
     * Endpoint para crear un nuevo Company
     *
     * @param company nuevo Entity que va a ser agregado a la lista existente de Companies
     * @return un mensaje de error o de confirmación, estatus y un CompanyDTO
     * @throws IllegalArgumentException        si algún atributo es nulo
     * @throws DataIntegrityViolationException si hay alguna irregularidad con el nuevo Entity
     *
     * @see HandlerExceptionController#illegalArgumentException(IllegalArgumentException)
     * @see HandlerExceptionController#dataIntegrityViolationException(DataIntegrityViolationException)
     */
    @PostMapping("/new")
    @SuppressWarnings("unused")
    public ResponseEntity<?> newCompany(@Valid @RequestBody Company company) {

        try {
            HashMap<String, Object> response = new HashMap<>();

            service.newCompany(company);

            CompanyDTO companyDTO = CompanyDTOMapper.getInstance().setCompanyDTOMapper(company).build();

            response.put("message", "Company created");
            response.put("companyDTO", companyDTO);

            return ResponseEntity.status(HttpStatus.OK).body(response);

        } catch (IllegalArgumentException ex) {

            ResponseEntity<ErrorDetails> errorResponse = HandlerExceptionController.illegalArgumentException(ex);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);

        } catch (DataIntegrityViolationException e) {

            ResponseEntity<ErrorDetails> errorResponse = HandlerExceptionController.dataIntegrityViolationException(e);
            return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
        }

    }

    /**
     * Endpoint que modifica un entity Company ya existente
     *
     * @param id      con el que se va a buscar el Entity a modificar
     * @param company es el Entity con todos sus atributos de los cuales algunos o todos
     *                van a ser modificados
     * @return un mensaje de error o confirmación, estatus y un CompanyDTO
     * @throws EntityNotFoundException         si no se encuentra el Entity a modificar
     * @throws IllegalArgumentException        si alguno de los atributos es nulo
     * @throws DataIntegrityViolationException si hay alguna irregularidad mientras se actualiza
     *                                         el entity.
     * @see HandlerExceptionController#entityNotFoundException(EntityNotFoundException)
     * @see HandlerExceptionController#illegalArgumentException(IllegalArgumentException)
     * @see HandlerExceptionController#dataIntegrityViolationException(DataIntegrityViolationException)
     */
    @PutMapping("/update/{id}")
    @SuppressWarnings("unused")
    public ResponseEntity<?> updateCompany(@PathVariable Integer id, @RequestBody Company company) {


        try {
            HashMap<String, Object> response = new HashMap<>();
            CompanyDTO companyDTO = service.updateCompany(company, id);
            response.put("message", "company updated successfully");
            response.put("companyDTO", companyDTO);
            return ResponseEntity.status(HttpStatus.OK).body(response);

        } catch (EntityNotFoundException ex) {
            ResponseEntity<ErrorDetails> errorResponse = HandlerExceptionController.entityNotFoundException(ex);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        } catch (IllegalArgumentException e) {
            ResponseEntity<ErrorDetails> errorResponse = HandlerExceptionController.illegalArgumentException(e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        } catch (DataIntegrityViolationException e) {
            ResponseEntity<ErrorDetails> errorResponse = HandlerExceptionController.dataIntegrityViolationException(e);
            return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
        }

    }

    /**
     * Endpoint que elimina un entity Company
     *  * Este método se utiliza para eliminar una entidad de tipo Company del sistema
     *  * basado en el ID proporcionado. El ID se utiliza para identificar de manera
     *  * única la entidad que se desea eliminar. Si la entidad no existe, se lanzará
     *  * una excepción.
     *
     * @param id con el que se va a identificar el Entity que va a ser eliminado
     * @return una ResponseEntity que contiene un mensaje de confirmación y un estado HTTP:
     * - 200 OK: Si la eliminación fue exitosa.
     * - 404 NOT FOUND: Si la entidad a eliminar no existe.
     * - 400 BAD REQUEST: Si el ID proporcionado es nulo o inválido.
     * - 409 CONFLICT: Si se presenta alguna irregularidad mientras se elimina el Entity.
     * @throws EntityNotFoundException         si el entity a eliminar no existe
     * @throws IllegalArgumentException        si el ID es nulo
     * @throws DataIntegrityViolationException si se presenta alguna irregularidad mientras
     *                                         se elimina el Entity
     * @see HandlerExceptionController#entityNotFoundException(EntityNotFoundException)
     * @see HandlerExceptionController#illegalArgumentException(IllegalArgumentException)
     * @see HandlerExceptionController#dataIntegrityViolationException(DataIntegrityViolationException)
     */
    @DeleteMapping("/delete/{id}")
    @SuppressWarnings("unused")
    public ResponseEntity<?> deleteCompanyById(@PathVariable Integer id) {

        try {
            HashMap<String, Object> response = new HashMap<>();
            service.deleteCompanyById(id);
            response.put("message", "Company with id " + id + " deleted");
            return ResponseEntity.status(HttpStatus.OK).body(response);

        } catch (EntityNotFoundException ex) {
            ResponseEntity<ErrorDetails> errorResponse = HandlerExceptionController.entityNotFoundException(ex);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);

        } catch (IllegalArgumentException ex) {
            ResponseEntity<ErrorDetails> errorResponse = HandlerExceptionController.illegalArgumentException(ex);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);

        } catch (DataIntegrityViolationException ex) {
            ResponseEntity<ErrorDetails> errorResponse = HandlerExceptionController.dataIntegrityViolationException(ex);
            return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
        }

    }

    /**
     * Endpoint que elimina todos los Entity Company
     *
     * @return un mensaje de error o confirmación y un estatus
     * @throws ResourceNotFoundException si la lista de entities a eliminar
     *                                   está vacía o no existe
     * @see HandlerExceptionController#handleResourceNotFoundException(ResourceNotFoundException)
     */
    @DeleteMapping("/delete_all")
    @SuppressWarnings("unused")
    public ResponseEntity<?> deleteAllCompanies() {

        try {

            HashMap<String, Object> response = new HashMap<>();
            service.deleteAllCompanies();
            response.put("message", "All companies deleted.Empty list");
            return ResponseEntity.status(HttpStatus.OK).body(response);

        } catch (ResourceNotFoundException ex) {
            ResponseEntity<ErrorDetails> errorResponse = HandlerExceptionController.handleResourceNotFoundException(ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }

    }


}
