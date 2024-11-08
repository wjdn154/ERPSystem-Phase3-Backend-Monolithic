package com.megazone.ERPSystem_phase3_Monolithic.hr.service.salary_ledger;

import com.megazone.ERPSystem_phase3_Monolithic.hr.model.salary_ledger.dto.*;

import java.util.List;

public interface SalaryLedgerService {
    SalaryLedgerDTO showSalaryLedger(SalaryLedgerSearchDTO dto);

    FinalizedDTO salaryLedgerDeadLineOn(Long salaryLedgerId);

    FinalizedDTO salaryLedgerDeadLineOff(Long salaryLedgerId);

    SalaryLedgerDTO salaryLedgerCalculator(Long salaryLedgerId);

    SalaryLedgerDTO updateSalaryLedger(SalaryLedgerDTO dto);

    List<PaymentStatusManagementShowDTO> showPaymentStatusManagement(PaymentStatusManagementSearchDTO dto);
}
