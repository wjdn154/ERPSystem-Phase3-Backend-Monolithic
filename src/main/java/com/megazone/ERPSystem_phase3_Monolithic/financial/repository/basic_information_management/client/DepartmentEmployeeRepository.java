package com.megazone.ERPSystem_phase3_Monolithic.financial.repository.basic_information_management.client;

import com.megazone.ERPSystem_phase3_Monolithic.financial.model.basic_information_management.client.ContactInfo;
import com.megazone.ERPSystem_phase3_Monolithic.financial.model.basic_information_management.client.DepartmentEmployee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentEmployeeRepository extends JpaRepository<DepartmentEmployee, Long> {
}