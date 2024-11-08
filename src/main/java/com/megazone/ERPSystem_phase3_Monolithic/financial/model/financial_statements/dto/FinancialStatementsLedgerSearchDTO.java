package com.megazone.ERPSystem_phase3_Monolithic.financial.model.financial_statements.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.YearMonth;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class FinancialStatementsLedgerSearchDTO {
    private YearMonth yearMonth;
}
