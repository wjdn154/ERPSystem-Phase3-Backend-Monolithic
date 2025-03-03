package com.megazone.ERPSystem_phase3_Monolithic.financial.model.voucher_entry.sales_and_purchase_voucher_entry.dto;

import com.megazone.ERPSystem_phase3_Monolithic.financial.model.voucher_entry.sales_and_purchase_voucher_entry.VatType;
import com.megazone.ERPSystem_phase3_Monolithic.financial.model.voucher_entry.sales_and_purchase_voucher_entry.enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class VatTypeShowDTO {
    private String vatTypeCode;
    private String vatTypeName;
    private String description;
    private TransactionType transactionType;

    public static VatTypeShowDTO create(VatType vatType) {
        return new VatTypeShowDTO(
                vatType.getCode(),
                vatType.getVatName(),
                vatType.getDescription(),
                vatType.getTransactionType()
        );
    }
}
