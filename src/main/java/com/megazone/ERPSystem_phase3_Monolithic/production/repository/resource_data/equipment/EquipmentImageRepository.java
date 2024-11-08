package com.megazone.ERPSystem_phase3_Monolithic.production.repository.resource_data.equipment;

import com.megazone.ERPSystem_phase3_Monolithic.production.model.resource_data.equipment.EquipmentDataImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EquipmentImageRepository extends JpaRepository<EquipmentDataImage, Long>, EquipmentImageRepositoryCustom {
}
