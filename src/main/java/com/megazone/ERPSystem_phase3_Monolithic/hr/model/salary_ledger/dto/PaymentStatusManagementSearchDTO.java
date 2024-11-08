package com.megazone.ERPSystem_phase3_Monolithic.hr.model.salary_ledger.dto;

import com.megazone.ERPSystem_phase3_Monolithic.hr.model.salary_ledger.PaymentStatusType;
import lombok.Data;

@Data
public class PaymentStatusManagementSearchDTO {
    private Long salaryStartId;
    private Long salaryEndId;
    private PaymentStatusType paymentStatusType;
}
