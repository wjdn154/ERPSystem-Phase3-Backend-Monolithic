package com.megazone.ERPSystem_phase3_Monolithic.financial.model.voucher_entry.general_voucher_entry.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UnresolvedVoucherDeleteDTO {
    private LocalDate searchDate;
    private List<Long> searchVoucherNumList;
    private Long ManagerId; // 담당자 ID
}
