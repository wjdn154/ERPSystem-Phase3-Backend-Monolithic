package com.megazone.ERPSystem_phase3_Monolithic.financial.service.ledger;

import com.megazone.ERPSystem_phase3_Monolithic.financial.model.ledger.dto.DailyAndMonthJournalSearchDTO;
import com.megazone.ERPSystem_phase3_Monolithic.financial.model.ledger.dto.DailyAndMonthJournalShowDTO;

import java.util.List;

public interface DailyAndMonthJournalService {
    List<DailyAndMonthJournalShowDTO> dailyLedgerShow(DailyAndMonthJournalSearchDTO dto);
}
