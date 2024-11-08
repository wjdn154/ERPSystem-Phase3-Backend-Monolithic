package com.megazone.ERPSystem_phase3_Monolithic.logistics.repository.sales_management.quotation;

import com.megazone.ERPSystem_phase3_Monolithic.logistics.model.purchase_management.dto.SearchDTO;
import com.megazone.ERPSystem_phase3_Monolithic.logistics.model.sales_management.Quotation;
import com.megazone.ERPSystem_phase3_Monolithic.logistics.model.sales_management.SalePlan;

import java.util.List;

public interface QuotationRepositoryCustom {
    List<Quotation> findBySearch(SearchDTO dto);
}
