package com.megazone.ERPSystem_phase3_Monolithic.hr.service.basic_configuration;


import com.megazone.ERPSystem_phase3_Monolithic.hr.model.basic_configuration.dto.SalaryStepEntryDTO;
import com.megazone.ERPSystem_phase3_Monolithic.hr.model.basic_configuration.dto.SalaryStepShowDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface SalaryStepService {
    List<SalaryStepShowDTO> show();

    String entry(SalaryStepEntryDTO dto);
}
