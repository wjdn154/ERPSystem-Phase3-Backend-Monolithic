package com.megazone.ERPSystem_phase3_Monolithic.financial.service.ledger;

import com.megazone.ERPSystem_phase3_Monolithic.financial.model.ledger.dto.*;

import java.util.List;

public interface GeneralService {
    List<GeneralAccountListDTO> getGeneralShow(GeneralDTO dto);

    GeneralShowAllDTO getGeneralSelectShow(GeneralSelectDTO dto);
}
