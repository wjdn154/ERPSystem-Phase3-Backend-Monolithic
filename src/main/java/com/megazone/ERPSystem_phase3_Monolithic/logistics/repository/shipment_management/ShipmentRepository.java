package com.megazone.ERPSystem_phase3_Monolithic.logistics.repository.shipment_management;

import com.megazone.ERPSystem_phase3_Monolithic.logistics.model.shipment_management.Shipment;
import com.megazone.ERPSystem_phase3_Monolithic.logistics.model.warehouse_management.warehouse.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShipmentRepository extends JpaRepository<Shipment, Long>, ShipmentRepositoryCustom {
    boolean existsByWarehouse(Warehouse warehouse);
}
