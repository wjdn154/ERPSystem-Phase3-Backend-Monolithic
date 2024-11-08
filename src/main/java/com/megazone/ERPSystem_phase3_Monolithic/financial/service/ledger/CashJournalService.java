package com.megazone.ERPSystem_phase3_Monolithic.financial.service.ledger;

import com.megazone.ERPSystem_phase3_Monolithic.financial.model.ledger.dto.CashJournalSearchDTO;
import com.megazone.ERPSystem_phase3_Monolithic.financial.model.ledger.dto.CashJournalShowAllDTO;
import com.megazone.ERPSystem_phase3_Monolithic.financial.model.ledger.dto.CashJournalShowAllListDTO;

public interface CashJournalService {
    CashJournalShowAllListDTO showAll(CashJournalSearchDTO dto);
}
