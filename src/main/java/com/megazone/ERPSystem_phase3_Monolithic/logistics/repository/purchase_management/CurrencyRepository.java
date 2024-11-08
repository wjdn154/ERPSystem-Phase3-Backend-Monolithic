package com.megazone.ERPSystem_phase3_Monolithic.logistics.repository.purchase_management;

import com.megazone.ERPSystem_phase3_Monolithic.logistics.model.purchase_management.Currency;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CurrencyRepository extends JpaRepository<Currency, Long> {
}
