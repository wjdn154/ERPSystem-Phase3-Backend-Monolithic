package com.megazone.ERPSystem_phase3_Monolithic.logistics.repository.sales_management.shipping_order_details;

import com.megazone.ERPSystem_phase3_Monolithic.logistics.model.sales_management.ShippingOrderDetail;

import java.time.LocalDate;
import java.util.List;

public interface ShippingOrderDetailRepositoryCustom {

    List<ShippingOrderDetail> findDetailsByOrderDateRange(LocalDate startDate, LocalDate endDate);
}
