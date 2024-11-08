package com.megazone.ERPSystem_phase3_Monolithic.logistics.repository.shipping_processing_management;

import com.megazone.ERPSystem_phase3_Monolithic.logistics.model.shipping_processing_management.ShippingProcessing;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShippingProcessingRepository extends JpaRepository<ShippingProcessing, Long>, ShippingProcessingRepositoryCustom {
}
