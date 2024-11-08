package com.megazone.ERPSystem_phase3_Monolithic.hr.model.basic_information_management.employee.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsersPermissionDTO {
    private Long id;
    private String usersId;
    private String userName;
    private Long employeeId;
    private String employeeNumber;
    private String employeeName;
    private Long permissionId;
}
