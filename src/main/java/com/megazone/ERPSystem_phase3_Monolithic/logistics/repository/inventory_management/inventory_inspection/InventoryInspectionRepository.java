package com.megazone.ERPSystem_phase3_Monolithic.logistics.repository.inventory_management.inventory_inspection;

import com.megazone.ERPSystem_phase3_Monolithic.logistics.model.inventory_management.inventory_adjustment.InventoryInspection;
import com.megazone.ERPSystem_phase3_Monolithic.logistics.model.warehouse_management.warehouse.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryInspectionRepository extends JpaRepository<InventoryInspection, Long>, InventoryInspectionRepositoryCustom {
    boolean existsByWarehouse(Warehouse warehouse);
}
