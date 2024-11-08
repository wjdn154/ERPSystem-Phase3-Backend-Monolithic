package com.megazone.ERPSystem_phase3_Monolithic.hr.service.basic_information_management.Position;


import com.megazone.ERPSystem_phase3_Monolithic.hr.model.basic_information_management.employee.Position;

import java.util.Optional;

public interface PositionService {
    Optional<Position> getPositionById(Long id);
}
