package com.megazone.ERPSystem_phase3_Monolithic.logistics.repository.shipping_processing_management;

import com.megazone.ERPSystem_phase3_Monolithic.logistics.model.shipping_processing_management.ShippingProcessing;

import java.time.LocalDate;
import java.util.List;

public interface ShippingProcessingRepositoryCustom {
    Long getMaxShippingNumberByDate(LocalDate parse);

    List<ShippingProcessing> findShippingProcessingByDateRangeAndStatus(LocalDate startDate, LocalDate endDate);
}
