package com.megazone.ERPSystem_phase3_Monolithic.database_config;

import com.megazone.ERPSystem_phase3_Monolithic.common.config.multi_tenant.SchemaBasedMultiTenantConnectionProvider;
import com.megazone.ERPSystem_phase3_Monolithic.common.config.multi_tenant.SchemaBasedTenantIdentifierResolver;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;


import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableJpaRepositories(
        basePackages = "com.megazone.ERPSystem_phase3_Monolithic",
        entityManagerFactoryRef = "entityManagerFactory",
        transactionManagerRef = "transactionManager"
)
public class JpaConfig {

    @Autowired
    private DataSource dynamicDataSource;

    @Autowired
    private SchemaBasedMultiTenantConnectionProvider multiTenantConnectionProvider;

    @Autowired
    private SchemaBasedTenantIdentifierResolver tenantIdentifierResolver;

    @Autowired
    private Environment environment; // Spring 환경 설정 주입

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(EntityManagerFactoryBuilder builder) {
        Map<String, Object> hibernateProps = new HashMap<>();
        hibernateProps.put("hibernate.multiTenancy", "SCHEMA");
        hibernateProps.put("hibernate.multi_tenant_connection_provider", multiTenantConnectionProvider);
        hibernateProps.put("hibernate.tenant_identifier_resolver", tenantIdentifierResolver);
//        hibernateProps.put("hibernate.physical_naming_strategy", "org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl");
        hibernateProps.put("hibernate.physical_naming_strategy", "com.megazone.ERPSystem_phase3_Monolithic.database_config.NamingCustom");

//        System.out.println("Naming Strategy from Environment1: " +
//                environment.getProperty("spring.jpa.hibernate.naming.physical-strategy"));
//
//        String property = environment.getProperty("spring.jpa.hibernate.naming.physical-strategy");
//        if (property != null) {
//            hibernateProps.put("spring.jpa.hibernate.naming.physical-strategy", property);
//        }
//
//        System.out.println("Naming Strategy from Environment2: " +
//                environment.getProperty("spring.jpa.hibernate.naming.physical-strategy"));

        return builder
                .dataSource(dynamicDataSource)
                .packages("com.megazone.ERPSystem_phase3_Monolithic") // 엔티티 패키지 경로
                .persistenceUnit("default")
                .properties(hibernateProps)
                .build();
    }

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}
