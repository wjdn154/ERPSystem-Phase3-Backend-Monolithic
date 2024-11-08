package com.megazone.ERPSystem_phase3_Monolithic.financial.repository.voucher_entry.sales_and_purchase_voucher_entry.jorunalEntryTypeSetup;

import com.megazone.ERPSystem_phase3_Monolithic.financial.model.basic_information_management.account_subject.AccountSubject;
import com.megazone.ERPSystem_phase3_Monolithic.financial.model.voucher_entry.sales_and_purchase_voucher_entry.JournalEntryTypeSetup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JournalEntryTypeSetupRepository extends JpaRepository<JournalEntryTypeSetup,Long> {
    boolean existsByAccountSubjectAndIdNot(AccountSubject accountSubject, Long id);
}
