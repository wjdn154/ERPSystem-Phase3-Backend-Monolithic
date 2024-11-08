package com.megazone.ERPSystem_phase3_Monolithic.logistics.repository.receiving_processing_management;

import java.time.LocalDate;

public interface ReceivingScheduleRepositoryCustom {
    Long findMaxPendingInventoryNumber();

    Long findMaxReceivingDateNumberByDate(LocalDate receivingDate);
}
