package com.megazone.ERPSystem_phase3_Monolithic.financial.service.ledger;

import com.megazone.ERPSystem_phase3_Monolithic.financial.model.ledger.dto.TaxInvoiceLedgerSearchDTO;
import com.megazone.ERPSystem_phase3_Monolithic.financial.model.ledger.dto.TaxInvoiceLedgerShowAllDTO;
import com.megazone.ERPSystem_phase3_Monolithic.financial.model.ledger.dto.TaxInvoiceLedgerShowDTO;

import java.util.List;

public interface TaxInvoiceLedgerService {
    List<TaxInvoiceLedgerShowAllDTO> show(TaxInvoiceLedgerSearchDTO dto);
}
