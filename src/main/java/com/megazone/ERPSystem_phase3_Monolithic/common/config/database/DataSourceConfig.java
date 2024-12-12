package com.megazone.ERPSystem_phase3_Monolithic.common.config.database;

import com.megazone.ERPSystem_phase3_Monolithic.common.config.SecretManagerConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class DataSourceConfig {

    private final SecretManagerConfig secretManagerConfig;

    @Bean
    public DataSource writerDataSource() {
        return createDataSource(secretManagerConfig.getWriterSecret());
    }

    @Bean
    public DataSource readerDataSource() {
        return createDataSource(secretManagerConfig.getReaderSecret());
    }

    private DataSource createDataSource(DatabaseCredentials credentials) {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(credentials.getUrl());
        dataSource.setUsername(credentials.getUsername());
        dataSource.setPassword(credentials.getPassword());
        return dataSource;
//        try {
//            // Spring의 SimpleDriverDataSource 사용
//            SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
//            dataSource.setDriverClass(com.mysql.cj.jdbc.Driver.class); // MySQL Driver 설정
//            dataSource.setUrl("jdbc:mysql://localhost:3306/PUBLIC?useSSL=false&serverTimezone=Asia/Seoul");
//            dataSource.setUsername("root");
//            dataSource.setPassword("1234");
//            return dataSource;
//        } catch (Exception e) {
//            log.error("Failed to create DataSource for URL: {}", credentials.getUrl(), e);
//            throw new RuntimeException("Failed to create DataSource", e);
//        }
    }

    @Bean
    @Primary
    public DataSource dynamicDataSource(
            @Qualifier("writerDataSource") DataSource writerDataSource,
            @Qualifier("readerDataSource") DataSource readerDataSource) {
        Map<Object, Object> dataSourceMap = new HashMap<>();
        dataSourceMap.put("writer", writerDataSource);
        dataSourceMap.put("reader", readerDataSource);

        AbstractRoutingDataSource routingDataSource = new AbstractRoutingDataSource() {
            @Override
            protected Object determineCurrentLookupKey() {
                return DataSourceContext.getCurrentDataSource();
            }
        };

        routingDataSource.setDefaultTargetDataSource(writerDataSource);
        routingDataSource.setTargetDataSources(dataSourceMap);
        routingDataSource.afterPropertiesSet();

        return routingDataSource;
    }
}