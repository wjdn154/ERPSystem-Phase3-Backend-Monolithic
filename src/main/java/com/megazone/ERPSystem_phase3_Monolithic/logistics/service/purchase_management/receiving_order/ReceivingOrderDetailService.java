package com.megazone.ERPSystem_phase3_Monolithic.logistics.service.purchase_management.receiving_order;

import com.megazone.ERPSystem_phase3_Monolithic.logistics.model.receiving_processing_management.dto.ReceivingOrderDetailResponseDTO;

import java.time.LocalDate;
import java.util.List;

public interface ReceivingOrderDetailService {
    List<ReceivingOrderDetailResponseDTO> getReceivingOrderDetailsWithWaitingQuantityByDateRange(LocalDate startDate, LocalDate endDate);
}
