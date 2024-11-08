package com.megazone.ERPSystem_phase3_Monolithic.Integrated.repository.dashboard;

import com.megazone.ERPSystem_phase3_Monolithic.Integrated.model.dashboard.RecentActivity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecentActivityRepository extends JpaRepository<RecentActivity, Long> {
    List<RecentActivity> findAllByOrderByActivityTimeDesc();
}