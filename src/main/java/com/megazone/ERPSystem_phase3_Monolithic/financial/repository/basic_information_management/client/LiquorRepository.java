package com.megazone.ERPSystem_phase3_Monolithic.financial.repository.basic_information_management.client;

import com.megazone.ERPSystem_phase3_Monolithic.financial.model.basic_information_management.client.Liquor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LiquorRepository extends JpaRepository<Liquor, Long> {
}