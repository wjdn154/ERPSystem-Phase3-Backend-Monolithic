package com.megazone.ERPSystem_phase3_Monolithic.hr.model.basic_information_management.employee.dto;

import lombok.Data;

@Data
public class UserPermissionUpdateRequestDTO {
    private String username;
    private PermissionDTO permissionDTO;
}
