package com.megazone.ERPSystem_phase3_Monolithic.logistics.repository.purchase_management.purchase_request;

import com.megazone.ERPSystem_phase3_Monolithic.logistics.model.purchase_management.PurchaseRequest;
import com.megazone.ERPSystem_phase3_Monolithic.logistics.model.purchase_management.dto.SearchDTO;

import java.util.List;

public interface PurchaseRequestRepositoryCustom {

    List<PurchaseRequest> findBySearch(SearchDTO dto);
}
