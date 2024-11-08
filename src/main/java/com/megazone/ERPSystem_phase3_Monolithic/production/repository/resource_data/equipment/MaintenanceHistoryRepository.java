package com.megazone.ERPSystem_phase3_Monolithic.production.repository.resource_data.equipment;

import com.megazone.ERPSystem_phase3_Monolithic.production.model.resource_data.equipment.MaintenanceHistory;
import com.megazone.ERPSystem_phase3_Monolithic.production.model.resource_data.equipment.dto.ListMaintenanceHistoryDTO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MaintenanceHistoryRepository
        extends JpaRepository<MaintenanceHistory, Long>, MaintenanceHistoryRepositoryCustom {

        List<MaintenanceHistory> findAllByOrderByMaintenanceDateDesc();

}
