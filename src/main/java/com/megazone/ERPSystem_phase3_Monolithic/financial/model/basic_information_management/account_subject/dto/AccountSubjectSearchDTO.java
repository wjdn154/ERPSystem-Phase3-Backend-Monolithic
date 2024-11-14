package com.megazone.ERPSystem_phase3_Monolithic.financial.model.basic_information_management.account_subject.dto;

import com.megazone.ERPSystem_phase3_Monolithic.financial.model.basic_information_management.account_subject.enums.EntryType;
import com.megazone.ERPSystem_phase3_Monolithic.financial.model.basic_information_management.account_subject.enums.IncreaseDecreaseType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountSubjectSearchDTO {
    private String code;
    private String name;
}