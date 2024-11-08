package com.megazone.ERPSystem_phase3_Monolithic.financial.repository.basic_information_management.bank_account;

import com.megazone.ERPSystem_phase3_Monolithic.financial.model.basic_information_management.bank_account.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {
}