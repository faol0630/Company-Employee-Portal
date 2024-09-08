package com.faol.security.dto.mapper;

import com.faol.security.dto.CompanyDTO;
import com.faol.security.entity.Company;


/**
 * Clase que contiene un metodo el cual a partir de un entity Company crea un CompanyDTO
 */
public class CompanyDTOMapper {

    //3) declaracion del entity:
    private Company company;

    //1) private constructor:
    private CompanyDTOMapper(){

    }

    //2) metodo que obtiene instancia de esta clase:(hasta aca es un patron singleton)
    public static CompanyDTOMapper getInstance(){
        return new CompanyDTOMapper();
    }

    //4) metodo set del entity modificado para que return un objeto de esta clase:
    public CompanyDTOMapper setCompanyDTOMapper(Company company){
        this.company = company;
        return this;
    }

    //5) metodo que devuelve un DTO:

    /**
     * Metodo que crea un CompanyDTO a partir de un Company pasado como argumento
     * @return un CompanyDTO
     */
    public CompanyDTO build(){
        if (company == null){
            throw new IllegalArgumentException("parameter null or not found");
        }

        return CompanyDTO.builder()
                .companyName(company.getCompany_name())
                .country(company.getCountry())
                .address(company.getAddress())
                .departmentList(company.getDepartmentList())
                .build();
    }
}
