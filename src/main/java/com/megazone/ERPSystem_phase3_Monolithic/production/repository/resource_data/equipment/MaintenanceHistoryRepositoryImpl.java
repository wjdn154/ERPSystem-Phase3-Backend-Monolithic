package com.megazone.ERPSystem_phase3_Monolithic.production.repository.resource_data.equipment;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MaintenanceHistoryRepositoryImpl implements MaintenanceHistoryRepositoryCustom{

    private final JPAQueryFactory queryFactory;
}
