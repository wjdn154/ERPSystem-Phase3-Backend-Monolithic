package com.megazone.ERPSystem_phase3_Monolithic.production.repository.work_performance.quality_control.QualityInspection;

import com.megazone.ERPSystem_phase3_Monolithic.production.model.work_performance.quality_control.InspectedProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InspectedProductRepository extends JpaRepository<InspectedProduct, Long> ,InspectedProductRepositoryCustom{
}
