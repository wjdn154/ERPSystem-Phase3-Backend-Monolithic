package com.megazone.ERPSystem_phase3_Monolithic.financial.repository.basic_information_management.account_subject;

import com.megazone.ERPSystem_phase3_Monolithic.financial.model.basic_information_management.account_subject.FixedMemo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FixedMemoRepository extends JpaRepository<FixedMemo, Long> {
}