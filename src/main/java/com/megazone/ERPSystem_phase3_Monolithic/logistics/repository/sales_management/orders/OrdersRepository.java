package com.megazone.ERPSystem_phase3_Monolithic.logistics.repository.sales_management.orders;

import com.megazone.ERPSystem_phase3_Monolithic.logistics.model.sales_management.Orders;
import com.megazone.ERPSystem_phase3_Monolithic.logistics.model.sales_management.Quotation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdersRepository extends JpaRepository<Orders, Long>, OrdersRepositoryCustom {
}
