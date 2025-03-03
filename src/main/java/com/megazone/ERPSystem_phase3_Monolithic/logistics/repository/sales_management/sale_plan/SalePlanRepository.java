package com.megazone.ERPSystem_phase3_Monolithic.logistics.repository.sales_management.sale_plan;

import com.megazone.ERPSystem_phase3_Monolithic.logistics.model.sales_management.SalePlan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SalePlanRepository extends JpaRepository<SalePlan, Long>, SalePlanRepositoryCustom {
}
