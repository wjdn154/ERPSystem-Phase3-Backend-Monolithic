package com.megazone.ERPSystem_phase3_Monolithic.database_config;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Aspect
@Component
public class DataSourceRoutingAspect {

    @Before("@annotation(transactional)")
    public void setDataSource(Transactional transactional) {
        if (transactional.readOnly()) {
            DataSourceContext.setCurrentDataSource("reader"); // 읽기 전용 트랜잭션은 Reader로 설정
            System.out.println("reader");
        } else {
            DataSourceContext.setCurrentDataSource("writer"); // 쓰기 트랜잭션은 Writer로 설정
            System.out.println("writer");
        }
    }

    @After("@annotation(org.springframework.transaction.annotation.Transactional)")
    public void clearDataSource() {
        DataSourceContext.clear(); // 트랜잭션 종료 후 데이터소스 컨텍스트 초기화
    }
}
