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
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class DataSourceConfig {

    private final SecretManagerConfig secretManagerConfig;

    @Bean(name = "writerDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.writer")
    public DataSource writerDataSource() {
        HikariDataSource dataSource = new HikariDataSource();

        DatabaseCredentials writerCredentials = secretManagerConfig.getWriterSecret();
        if (writerCredentials != null) {
            dataSource.setJdbcUrl(writerCredentials.getUrl());
            dataSource.setUsername(writerCredentials.getUsername());
            dataSource.setPassword(writerCredentials.getPassword());
        }

        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setMaximumPoolSize(10);
        dataSource.setMinimumIdle(5);

        return dataSource;
    }

    @Bean(name = "readerDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.reader")
    public DataSource readerDataSource() {
        HikariDataSource dataSource = new HikariDataSource();

        DatabaseCredentials readerCredentials = secretManagerConfig.getReaderSecret();
        if (readerCredentials != null) {
            dataSource.setJdbcUrl(readerCredentials.getUrl());
            dataSource.setUsername(readerCredentials.getUsername());
            dataSource.setPassword(readerCredentials.getPassword());
        }
        return dataSource;
    }

    @Bean
    @Primary
    public DataSource dynamicDataSource(
            @Qualifier("writerDataSource") DataSource writerDataSource,
            @Qualifier("readerDataSource") DataSource readerDataSource) {

        Map<Object, Object> dataSourceMap = new HashMap<>();
        dataSourceMap.put("writer", writerDataSource);
        dataSourceMap.put("reader", readerDataSource);

        AbstractRoutingDataSource routingDataSource = new DynamicDataSource();
        routingDataSource.setDefaultTargetDataSource(writerDataSource); // 기본 데이터소스: Writer
        routingDataSource.setTargetDataSources(dataSourceMap);

        return routingDataSource;
    }
}