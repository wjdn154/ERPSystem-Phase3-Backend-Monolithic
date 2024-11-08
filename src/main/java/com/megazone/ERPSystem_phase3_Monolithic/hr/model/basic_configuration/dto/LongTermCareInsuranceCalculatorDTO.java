package com.megazone.ERPSystem_phase3_Monolithic.hr.model.basic_configuration.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LongTermCareInsuranceCalculatorDTO {
    private BigDecimal healthInsurancePensionAmount;
    private Long longTermCareInsuranceId;
}
