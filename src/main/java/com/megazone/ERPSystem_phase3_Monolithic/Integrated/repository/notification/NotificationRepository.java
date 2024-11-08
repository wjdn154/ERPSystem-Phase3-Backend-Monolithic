package com.megazone.ERPSystem_phase3_Monolithic.Integrated.repository.notification;

import com.megazone.ERPSystem_phase3_Monolithic.Integrated.model.dashboard.RecentActivity;
import com.megazone.ERPSystem_phase3_Monolithic.Integrated.model.notification.Notification;
import com.megazone.ERPSystem_phase3_Monolithic.Integrated.model.notification.UserNotification;
import com.megazone.ERPSystem_phase3_Monolithic.Integrated.model.notification.enums.ModuleType;
import com.megazone.ERPSystem_phase3_Monolithic.Integrated.model.notification.enums.PermissionType;
import com.megazone.ERPSystem_phase3_Monolithic.hr.model.basic_information_management.employee.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long>, NotificationRepositoryCustom {
    List<Notification> findByModuleAndPermission(ModuleType module, PermissionType permission);
}