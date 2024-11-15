package com.megazone.ERPSystem_phase3_Monolithic.financial.model.basic_information_management.account_subject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountSubjectSearchDTO {
    private String name;
    private String code;
}