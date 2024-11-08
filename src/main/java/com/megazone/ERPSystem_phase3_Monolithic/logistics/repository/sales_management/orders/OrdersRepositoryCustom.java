package com.megazone.ERPSystem_phase3_Monolithic.logistics.repository.sales_management.orders;

import com.megazone.ERPSystem_phase3_Monolithic.logistics.model.purchase_management.dto.SearchDTO;
import com.megazone.ERPSystem_phase3_Monolithic.logistics.model.sales_management.Orders;

import java.util.List;

public interface OrdersRepositoryCustom {
    List<Orders> findBySearch(SearchDTO dto);
}
