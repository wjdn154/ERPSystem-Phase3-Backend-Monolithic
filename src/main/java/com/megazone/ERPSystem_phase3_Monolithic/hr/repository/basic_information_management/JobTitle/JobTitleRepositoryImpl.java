package com.megazone.ERPSystem_phase3_Monolithic.hr.repository.basic_information_management.JobTitle;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class JobTitleRepositoryImpl implements JobTitleRepositoryCustom{
    private final JPAQueryFactory queryFactory;
}
