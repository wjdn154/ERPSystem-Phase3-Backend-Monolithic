package com.megazone.ERPSystem_phase3_Monolithic.production.model.work_performance.work_report.dto;

import com.megazone.ERPSystem_phase3_Monolithic.financial.model.ledger.enums.DailyAndMonthType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DailyAndMonthlyReportSearchDTO {
    private LocalDate startDate;
    private LocalDate endDate;
}
