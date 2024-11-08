package com.megazone.ERPSystem_phase3_Monolithic.logistics.repository.sales_management.shipping_order;

import com.megazone.ERPSystem_phase3_Monolithic.logistics.model.purchase_management.dto.SearchDTO;
import com.megazone.ERPSystem_phase3_Monolithic.logistics.model.sales_management.ShippingOrder;

import java.util.List;

public interface ShippingOrderRepositoryCustom {
    List<ShippingOrder> findBySearch(SearchDTO dto);
}
