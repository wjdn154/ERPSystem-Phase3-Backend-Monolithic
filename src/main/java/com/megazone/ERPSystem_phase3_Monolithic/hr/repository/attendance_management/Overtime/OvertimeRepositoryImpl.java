package com.megazone.ERPSystem_phase3_Monolithic.hr.repository.attendance_management.Overtime;


import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class OvertimeRepositoryImpl {
    private final JPAQueryFactory queryFactory;
}
