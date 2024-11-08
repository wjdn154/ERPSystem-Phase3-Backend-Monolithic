package com.megazone.ERPSystem_phase3_Monolithic.financial.repository.basic_information_management.credit_card;

import com.megazone.ERPSystem_phase3_Monolithic.financial.model.basic_information_management.credit_card.Ownership;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OwnershipRepository extends JpaRepository<Ownership, Long> {
}