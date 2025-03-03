package com.megazone.ERPSystem_phase3_Monolithic.production.model.work_performance.work_report.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**작업 실적 리스트 dto
 * */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WorkPerformanceListDTO {

    private Long id;   //아이디
    private BigDecimal quantity;    //실제 생산량

    private Long productionOrderId;       //작업지시 아이디
    private String productionOrderName;   //작업지시 이름

//    private String productCode;         //품목 코드
//    private String productName;         //품목 이름

}
