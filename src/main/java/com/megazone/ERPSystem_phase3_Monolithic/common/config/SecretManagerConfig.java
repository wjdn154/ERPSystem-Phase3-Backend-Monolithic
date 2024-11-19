package com.megazone.ERPSystem_phase3_Monolithic.common.config;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.megazone.ERPSystem_phase3_Monolithic.common.config.database.DatabaseCredentials;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueRequest;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueResponse;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Configuration
public class SecretManagerConfig {

    private SecretsManagerClient client;
    private Map<String, CacheEntry> secretCache = new ConcurrentHashMap<>();

    @PostConstruct
    public void init() {
        this.client = SecretsManagerClient.builder().build();
    }

    @PreDestroy
    public void destroy() {
        if (client != null) {
            client.close();
            log.info("SecretsManagerClient 리소스가 정상적으로 닫혔습니다.");
        }
    }

    public String getJwtSecret() {
        return getCachedSecret("omz-env-secrets-backend", "JWT_SECRET");
    }

    public DatabaseCredentials getWriterSecret() {
        return new DatabaseCredentials(
//                getCachedSecret("omz-env-secrets-backend", "RDS_WRITER_DB_URL"),
//                getCachedSecret("omz-env-secrets-backend", "RDS_DB_USER"),
//                getCachedSecret("omz-env-secrets-backend", "RDS_DB_PASSWORD")
                    getSecretValueFromJson("omz-env-secrets-backend", "DB_URL"),
                    getSecretValueFromJson("omz-env-secrets-backend", "DB_USER"),
                    getSecretValueFromJson("omz-env-secrets-backend", "DB_PASSWORD")
        );
    }

    public DatabaseCredentials getReaderSecret() {
        return new DatabaseCredentials(
//                getCachedSecret("omz-env-secrets-backend", "RDS_READER_DB_URL"),
//                getCachedSecret("omz-env-secrets-backend", "RDS_DB_USER"),
//                getCachedSecret("omz-env-secrets-backend", "RDS_DB_PASSWORD")
                    getSecretValueFromJson("omz-env-secrets-backend", "DB_URL"),
                    getSecretValueFromJson("omz-env-secrets-backend", "DB_USER"),
                    getSecretValueFromJson("omz-env-secrets-backend", "DB_PASSWORD")
        );
    }

    public String getSecretValueFromJson(String secretName, String key) {
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

    private String getCachedSecret(String secretName, String key) {
        String cacheKey = secretName + ":" + key;
        CacheEntry cacheEntry = secretCache.get(cacheKey);

        if (cacheEntry != null && !cacheEntry.isExpired()) {
            return cacheEntry.value;
        }

        String secretValue = getSecretValueFromJson(secretName, key);
        secretCache.put(cacheKey, new CacheEntry(secretValue, System.currentTimeMillis() + 60000)); // 1분 TTL
        return secretValue;
    }

    private static class CacheEntry {
        String value;
        long expireAt;

        CacheEntry(String value, long expireAt) {
            this.value = value;
            this.expireAt = expireAt;
        }

        boolean isExpired() {
            return System.currentTimeMillis() > expireAt;
        }
    }
}
