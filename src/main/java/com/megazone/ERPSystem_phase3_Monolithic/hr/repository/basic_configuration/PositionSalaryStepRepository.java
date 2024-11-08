package com.megazone.ERPSystem_phase3_Monolithic.hr.repository.basic_configuration;

import com.megazone.ERPSystem_phase3_Monolithic.hr.model.basic_configuration.PositionSalaryStep;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PositionSalaryStepRepository extends JpaRepository<PositionSalaryStep, Long> , PositionSalaryStepRepositoryCustom {

}
