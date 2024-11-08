package com.megazone.ERPSystem_phase3_Monolithic.logistics.repository.shipment_management;

import com.megazone.ERPSystem_phase3_Monolithic.logistics.model.shipment_management.ShipmentProduct;

import java.time.LocalDate;
import java.util.List;

public interface ShipmentProductRepositoryCustom {
    List<ShipmentProduct> findShipmentProductsByDateRange(LocalDate startDate, LocalDate endDate);

    Long findTotalQuantityByDateRange(LocalDate startDate, LocalDate endDate);
}
