package com.megazone.ERPSystem_phase3_Monolithic.production.repository.resource_data.MaterialHazardous;

import com.megazone.ERPSystem_phase3_Monolithic.production.model.resource_data.MaterialData;
import com.megazone.ERPSystem_phase3_Monolithic.production.model.resource_data.MaterialHazardous;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MaterialHazardousRepository extends JpaRepository<MaterialHazardous, Long> {

    void deleteAllByMaterialData(MaterialData materialData);
}
