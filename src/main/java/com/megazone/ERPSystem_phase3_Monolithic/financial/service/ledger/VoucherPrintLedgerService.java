package com.megazone.ERPSystem_phase3_Monolithic.financial.service.ledger;

import com.megazone.ERPSystem_phase3_Monolithic.financial.model.ledger.dto.VoucherPrintSearchDTO;
import com.megazone.ERPSystem_phase3_Monolithic.financial.model.voucher_entry.general_voucher_entry.dto.ResolvedVoucherShowDTO;

import java.util.List;

public interface VoucherPrintLedgerService {
    List<ResolvedVoucherShowDTO> VoucherPrintList(VoucherPrintSearchDTO dto);
}
