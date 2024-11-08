package com.megazone.ERPSystem_phase3_Monolithic.production.repository.resource_data.HazardousMaterial;

import com.megazone.ERPSystem_phase3_Monolithic.production.model.resource_data.HazardousMaterial;
import com.megazone.ERPSystem_phase3_Monolithic.production.model.resource_data.MaterialData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface HazardousMaterialRepository extends JpaRepository<HazardousMaterial, Long>, HazardousMaterialRepositoryCustom {


    boolean existsByHazardousMaterialCode(String hazardousMaterialCode);

    Optional<HazardousMaterial> findByHazardousMaterialCode(String hazardousMaterialCode);


    boolean existsByHazardousMaterialCodeAndIdNot(String hazardousMaterialCode, Long id);


}
