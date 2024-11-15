package com.megazone.ERPSystem_phase3_Monolithic.financial.model.basic_information_management.client.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class searchClientListDTO {
    private Long code; // id
    private String printClientName; // 상호명
}
