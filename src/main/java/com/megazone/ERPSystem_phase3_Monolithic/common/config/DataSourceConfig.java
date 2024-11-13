package com.megazone.ERPSystem_phase3_Monolithic.common.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import javax.sql.DataSource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.sql.Connection;
import java.sql.SQLException;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class DataSourceConfig {

    private final SecretManagerConfig secretManagerConfig;

    @Bean
    @Primary  // 기본 데이터 소스로 설정
    public DataSource dataSource() {
        DatabaseCredentials credentials = secretManagerConfig.getSecret();

        log.info("DB URL: " + credentials.getUrl());
        log.info("DB User: " + credentials.getUsername());
        log.info("DB PW: " + credentials.getPassword());

        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");

        dataSource.setUrl(credentials.getUrl());
        dataSource.setUsername(credentials.getUsername());
        dataSource.setPassword(credentials.getPassword());

        log.info("DataSource 빈이 생성되었습니다: " + dataSource);
        return dataSource;
    }

//    @Bean
//    public CommandLineRunner testDataSource(DataSource dataSource) {
//
//        System.out.println("============================================ testDataSource CommandLineRunner 실행 ============================================");
//
//        return args -> {
//            try (Connection conn = dataSource.getConnection()) {
//                System.out.println("DB 연결 시도: " + conn.getMetaData().getDatabaseProductName());
//            } catch (SQLException e) {
//                System.err.println("DB 연결 실패: " + e.getMessage());
//                e.printStackTrace();
//            }
//        };
//    }

}