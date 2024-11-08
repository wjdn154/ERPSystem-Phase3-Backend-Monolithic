package com.megazone.ERPSystem_phase3_Monolithic.financial.repository.basic_information_management.company;

import com.megazone.ERPSystem_phase3_Monolithic.financial.model.basic_information_management.company.TaxOffice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TaxOfficeRepository extends JpaRepository<TaxOffice, Long> {
    Optional<TaxOffice> findByCode(String code);
}