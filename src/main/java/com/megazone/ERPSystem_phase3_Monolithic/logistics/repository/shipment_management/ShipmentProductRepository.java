package com.megazone.ERPSystem_phase3_Monolithic.logistics.repository.shipment_management;

import com.megazone.ERPSystem_phase3_Monolithic.logistics.model.shipment_management.Shipment;
import com.megazone.ERPSystem_phase3_Monolithic.logistics.model.shipment_management.ShipmentProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShipmentProductRepository extends JpaRepository<ShipmentProduct, Long>, ShipmentProductRepositoryCustom {

    void deleteAllByShipment(Shipment existingShipment);

}
