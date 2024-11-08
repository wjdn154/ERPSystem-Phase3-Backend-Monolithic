package com.megazone.ERPSystem_phase3_Monolithic.hr.service.salary_ledger;

import com.megazone.ERPSystem_phase3_Monolithic.hr.model.salary_ledger.dto.SalaryLedgerDateShowDTO;

import java.util.List;

public interface SalaryLedgerDateService {
    List<SalaryLedgerDateShowDTO> showAll();
}
