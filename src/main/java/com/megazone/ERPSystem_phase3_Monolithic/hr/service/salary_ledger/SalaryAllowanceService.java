package com.megazone.ERPSystem_phase3_Monolithic.hr.service.salary_ledger;

import com.megazone.ERPSystem_phase3_Monolithic.hr.model.salary_ledger.dto.SalaryLedgerAllowanceShowDTO;

import java.util.List;

public interface SalaryAllowanceService {
    List<SalaryLedgerAllowanceShowDTO> findSalaryAllowances(Long salaryId);
}
