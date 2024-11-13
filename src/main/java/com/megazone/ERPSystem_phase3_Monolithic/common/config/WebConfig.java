package com.megazone.ERPSystem_phase3_Monolithic.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig {

    /**
     * CORS 설정을 위한 WebMvcConfigurer 빈을 생성함
     * @return WebMvcConfigurer를 반환함
     */
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {

            /**
             * CORS 매핑을 추가함
             * @param registry CORS 설정을 등록하는 객체
             */
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/api/**")
                        .allowedOrigins(
                                "http://omz-erp.click",
                                "https://omz-erp.click",
                                "http://www.omz-erp.click",
                                "https://www.omz-erp.click",
                                "http://localhost:3000"
                        )
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        .allowedHeaders("*")
                        .allowCredentials(true);
            }
        };

    }

}