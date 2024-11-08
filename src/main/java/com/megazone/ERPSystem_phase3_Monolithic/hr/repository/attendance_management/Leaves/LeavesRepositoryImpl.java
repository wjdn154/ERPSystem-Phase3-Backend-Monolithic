package com.megazone.ERPSystem_phase3_Monolithic.hr.repository.attendance_management.Leaves;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class LeavesRepositoryImpl implements LeavesRepositoryCustom {
    private final JPAQueryFactory queryFactory;
}
