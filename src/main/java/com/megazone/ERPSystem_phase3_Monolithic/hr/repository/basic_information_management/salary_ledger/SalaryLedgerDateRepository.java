package com.megazone.ERPSystem_phase3_Monolithic.hr.repository.basic_information_management.salary_ledger;

import com.megazone.ERPSystem_phase3_Monolithic.hr.model.salary_ledger.SalaryLedgerDate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SalaryLedgerDateRepository extends JpaRepository<SalaryLedgerDate, Long> ,SalaryLedgerDateRepositoryCustom{
}
