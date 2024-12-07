package com.megazone.ERPSystem_phase3_Monolithic.financial.service.basic_information_management.company;

import com.megazone.ERPSystem_phase3_Monolithic.financial.model.basic_information_management.company.Company;
import com.megazone.ERPSystem_phase3_Monolithic.financial.model.basic_information_management.company.dto.*;

import java.util.List;
import java.util.Optional;

public interface CompanyService {
    Optional<CompanyDTO> saveCompany(CompanyDTO companyDTO);
    Optional<CompanyDTO> updateCompany(Long id, CompanyDTO companyDTO);
    List<CompanyDTO> findAllCompany();
    List<CompanyDTO> searchCompany(String searchText);

}