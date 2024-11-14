package com.megazone.ERPSystem_phase3_Monolithic.common.config;

import com.zaxxer.hikari.HikariDataSource;
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

        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");

        dataSource.setJdbcUrl(credentials.getUrl());
        dataSource.setUsername(credentials.getUsername());
        dataSource.setPassword(credentials.getPassword());

        dataSource.setMaximumPoolSize(10);
        dataSource.setMinimumIdle(5);

        return dataSource;
    }
}