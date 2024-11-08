package com.megazone.ERPSystem_phase3_Monolithic.financial.service.ledger;

import com.megazone.ERPSystem_phase3_Monolithic.financial.model.ledger.dto.SalesAndPurChaseLedgerSearchDTO;
import com.megazone.ERPSystem_phase3_Monolithic.financial.model.ledger.dto.SalesAndPurChaseLedgerShowAllDTO;

import java.util.List;

public interface SalesAndPurchaseLedgerService {
    SalesAndPurChaseLedgerShowAllDTO showAll(SalesAndPurChaseLedgerSearchDTO dto);
}
