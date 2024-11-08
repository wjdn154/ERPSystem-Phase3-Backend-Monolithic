package com.megazone.ERPSystem_phase3_Monolithic.hr.service.basic_information_management.salary;

import com.megazone.ERPSystem_phase3_Monolithic.hr.model.basic_configuration.dto.EmploymentInsurancePensionShowDTO;
import com.megazone.ERPSystem_phase3_Monolithic.hr.model.basic_configuration.dto.InsurancePensionCalculatorDTO;

import java.math.BigDecimal;
import java.util.List;

public interface EmploymentInsurancePensionService {
    BigDecimal calculator(InsurancePensionCalculatorDTO dto);

    BigDecimal calculator(BigDecimal amount);

    List<EmploymentInsurancePensionShowDTO> showAll();
}
