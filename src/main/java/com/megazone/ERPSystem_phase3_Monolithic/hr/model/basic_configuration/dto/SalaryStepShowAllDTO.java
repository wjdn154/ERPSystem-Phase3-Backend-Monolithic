package com.megazone.ERPSystem_phase3_Monolithic.hr.model.basic_configuration.dto;

import com.megazone.ERPSystem_phase3_Monolithic.hr.model.basic_configuration.SalaryStep;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SalaryStepShowAllDTO {
    private List<SalaryStepShowDTO> salaryStepShowDTOS;

    public static SalaryStepShowAllDTO create(List<SalaryStepShowDTO> step) {
        return new SalaryStepShowAllDTO(step);
    }
}
