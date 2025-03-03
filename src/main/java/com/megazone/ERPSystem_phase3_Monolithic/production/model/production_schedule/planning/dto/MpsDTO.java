package com.megazone.ERPSystem_phase3_Monolithic.production.model.production_schedule.planning.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MpsDTO {
    private Long id; // PK
    private String name; // MPS명
    private LocalDate planDate; // 계획 수립 날짜
    private LocalDate startDate; // 생산 시작일
    private LocalDate endDate; // 생산 완료 예정일
    private String status; // 계획 상태 (계획, 확정, 진행 중, 완료)
    private Long productId; // 생산할 제품 ID
    private Long quantity; // 생산 수량
    private String remarks; // 추가 설명 또는 비고
    private Long productionRequestId; // 연관된 생산 의뢰 ID
    private Long ordersId; // 관련된 고객 주문 ID
    private Long saleId; // 관련된 판매 계획 ID
    private List<Long> productionOrderIds;
}
