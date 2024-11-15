package com.megazone.ERPSystem_phase3_Monolithic;

import com.megazone.ERPSystem_phase3_Monolithic.common.config.multi_tenant.SqlInitProperties;
import com.megazone.ERPSystem_phase3_Monolithic.common.config.multi_tenant.TenantService;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.Arrays;

@SpringBootApplication
public class ErpSystemPhase3MonolithicApplication {
	public static void main(String[] args) {

		SpringApplication.run(ErpSystemPhase3MonolithicApplication.class, args);
	}
}