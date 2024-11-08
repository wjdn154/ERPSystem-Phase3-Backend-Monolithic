package com.megazone.ERPSystem_phase3_Monolithic.hr.service.basic_information_management.salary;

import com.megazone.ERPSystem_phase3_Monolithic.hr.model.basic_configuration.dto.SalaryEntryDTO;
import com.megazone.ERPSystem_phase3_Monolithic.hr.model.basic_configuration.dto.SalaryShowDto;

public interface SalaryService {
    void saveSalary(SalaryEntryDTO dto);

    SalaryShowDto show(Long employeeId);
}
