package com.megazone.ERPSystem_phase3_Monolithic.production.repository.work_performance.goods_receipt.CompleteProduct;

import com.megazone.ERPSystem_phase3_Monolithic.production.model.work_performance.goods_receipt.CompletedProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompleteProductRepository extends JpaRepository<CompletedProduct, Long> ,CompleteProductRepositoryCustom{
}
