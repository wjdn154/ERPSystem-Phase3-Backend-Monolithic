package com.megazone.ERPSystem_phase3_Monolithic.common.config;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.megazone.ERPSystem_phase3_Monolithic.common.config.database.DatabaseCredentials;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueRequest;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueResponse;

@Slf4j
@Configuration
public class SecretManagerConfig {

    private SecretsManagerClient client;
    private String jwtSecret;
    private DatabaseCredentials writerCredentials;
    private DatabaseCredentials readerCredentials;

    @PostConstruct
    public void init() {
        this.client = SecretsManagerClient.builder().build();
    }

    /**
     * JWT Secret 가져오기
     */
    public String getJwtSecret() {
        if (jwtSecret == null) { // 캐싱되지 않았을 경우만 호출
            jwtSecret = getSecretValueFromJson("omz-env-secrets-backend", "JWT_SECRET");
        }
        return jwtSecret;
    }

    /**
     * Writer DB 시크릿 가져오기
     */
    public DatabaseCredentials getWriterSecret() {
        if (writerCredentials == null) { // 캐싱되지 않았을 경우만 호출
            writerCredentials = new DatabaseCredentials(
                    getSecretValueFromJson("omz-env-secrets-backend", "RDS_WRITER_DB_URL"),
                    getSecretValueFromJson("omz-env-secrets-backend", "RDS_DB_USER"),
                    getSecretValueFromJson("omz-env-secrets-backend", "RDS_DB_PASSWORD")
            );
        }
        return writerCredentials;
    }

    /**
     * Reader DB 시크릿 가져오기
     */
    public DatabaseCredentials getReaderSecret() {
        if (readerCredentials == null) { // 캐싱되지 않았을 경우만 호출
            readerCredentials = new DatabaseCredentials(
                    getSecretValueFromJson("omz-env-secrets-backend", "RDS_READER_DB_URL"),
                    getSecretValueFromJson("omz-env-secrets-backend", "RDS_DB_USER"),
                    getSecretValueFromJson("omz-env-secrets-backend", "RDS_DB_PASSWORD")
            );
        }
        return readerCredentials;
    }

    /**
     * Secrets Manager에서 특정 시크릿 이름과 키를 받아 JSON에서 값을 추출
     */
    private String getSecretValueFromJson(String secretName, String key) {
        try {
            GetSecretValueRequest getSecretValueRequest = GetSecretValueRequest.builder()
                    .secretId(secretName)
                    .build();
            GetSecretValueResponse getSecretValueResponse = client.getSecretValue(getSecretValueRequest);
            String secretString = getSecretValueResponse.secretString();

            // JSON 파싱을 통해 특정 키의 값만 추출
            ObjectMapper mapper = new ObjectMapper();
            JsonNode node = mapper.readTree(secretString);

            if (node.has(key)) {
                return node.get(key).asText();
            } else {
                throw new RuntimeException("시크릿 키 '" + key + "'를 찾을 수 없습니다: " + secretName);
            }
        } catch (Exception e) {
            log.error("Secrets Manager에서 시크릿 '{}'의 키 '{}'를 찾지 못했습니다: {}", secretName, key, e.getMessage(), e);
            throw new RuntimeException("Secrets Manager 호출에 실패했습니다: " + secretName, e);
        }
    }
}