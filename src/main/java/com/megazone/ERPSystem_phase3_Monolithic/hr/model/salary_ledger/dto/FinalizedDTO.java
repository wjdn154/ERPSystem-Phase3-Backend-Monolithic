package com.megazone.ERPSystem_phase3_Monolithic.hr.model.salary_ledger.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FinalizedDTO {
    private boolean finalized;
    private String message;
}
