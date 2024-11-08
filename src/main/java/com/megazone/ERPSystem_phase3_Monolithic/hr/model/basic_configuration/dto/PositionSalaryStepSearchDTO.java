package com.megazone.ERPSystem_phase3_Monolithic.hr.model.basic_configuration.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.YearMonth;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PositionSalaryStepSearchDTO {
    private Long positionId;
    private LocalDate endMonth;
}

