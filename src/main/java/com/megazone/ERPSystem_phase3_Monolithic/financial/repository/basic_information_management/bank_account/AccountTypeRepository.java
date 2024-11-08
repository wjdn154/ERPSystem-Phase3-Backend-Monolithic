package com.megazone.ERPSystem_phase3_Monolithic.financial.repository.basic_information_management.bank_account;

import com.megazone.ERPSystem_phase3_Monolithic.financial.model.basic_information_management.bank_account.AccountType;
import com.megazone.ERPSystem_phase3_Monolithic.financial.model.common.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountTypeRepository extends JpaRepository<AccountType, Long> {
}