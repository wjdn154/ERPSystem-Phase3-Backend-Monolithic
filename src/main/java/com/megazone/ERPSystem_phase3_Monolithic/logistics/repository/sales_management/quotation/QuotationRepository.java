package com.megazone.ERPSystem_phase3_Monolithic.logistics.repository.sales_management.quotation;

import com.megazone.ERPSystem_phase3_Monolithic.logistics.model.sales_management.Quotation;
import com.megazone.ERPSystem_phase3_Monolithic.logistics.model.sales_management.SalePlan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuotationRepository extends JpaRepository<Quotation, Long>, QuotationRepositoryCustom {
}
