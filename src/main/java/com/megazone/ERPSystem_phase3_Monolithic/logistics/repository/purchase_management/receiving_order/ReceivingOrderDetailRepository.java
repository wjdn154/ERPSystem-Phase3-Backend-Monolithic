package com.megazone.ERPSystem_phase3_Monolithic.logistics.repository.purchase_management.receiving_order;

import com.megazone.ERPSystem_phase3_Monolithic.logistics.model.purchase_management.ReceivingOrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReceivingOrderDetailRepository extends JpaRepository<ReceivingOrderDetail, Long>, ReceivingOrderDetailRepositoryCustom {
}
