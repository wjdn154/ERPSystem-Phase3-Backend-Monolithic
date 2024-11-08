package com.megazone.ERPSystem_phase3_Monolithic.hr.repository.basic_information_management.Department;

import com.megazone.ERPSystem_phase3_Monolithic.hr.model.basic_information_management.employee.dto.DepartmentShowDTO;

import java.util.List;

public interface DepartmentRepositoryCustom {
    List<DepartmentShowDTO> findAllDepartments();
}
