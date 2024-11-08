package com.megazone.ERPSystem_phase3_Monolithic.financial.repository.common;

import com.megazone.ERPSystem_phase3_Monolithic.financial.model.basic_information_management.company.Address;
import com.megazone.ERPSystem_phase3_Monolithic.financial.model.common.Bank;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankRepository extends JpaRepository<Bank, Long> {
}