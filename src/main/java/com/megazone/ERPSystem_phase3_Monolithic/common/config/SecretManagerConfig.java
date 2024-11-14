package com.megazone.ERPSystem_phase3_Monolithic.common.config;

 import com.fasterxml.jackson.databind.JsonNode;
 import com.fasterxml.jackson.databind.ObjectMapper;
 import jakarta.annotation.PostConstruct;
 import lombok.Getter;
 import lombok.extern.slf4j.Slf4j;
 import org.springframework.beans.factory.annotation.Value;
 import org.springframework.context.annotation.Bean;
 import org.springframework.context.annotation.Configuration;
 import software.amazon.awssdk.regions.Region;
 import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;
 import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueRequest;
 import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueResponse;

@Slf4j
@Configuration
public class SecretManagerConfig {

    private String jwtSecret;
    private SecretsManagerClient client;

    @PostConstruct
    public void init() {
        this.client = SecretsManagerClient.builder().build();
    }

    public String getJwtSecret() {
        if (jwtSecret == null) {  // 캐싱하지 않은 경우에만 Secrets Manager 호출
            String secretName = "omz-env-secrets-backend";
            GetSecretValueRequest getSecretValueRequest = GetSecretValueRequest.builder()
                    .secretId(secretName)
                    .build();
            GetSecretValueResponse getSecretValueResponse = client.getSecretValue(getSecretValueRequest);
            ObjectMapper mapper = new ObjectMapper();
            try {
                JsonNode node = mapper.readTree(getSecretValueResponse.secretString());
                jwtSecret = node.get("JWT_SECRET").asText();
            } catch (Exception e) {
                throw new RuntimeException("JWT_SECRET secret JSON 구문 분석에 실패했습니다.", e);
            }
        }
        return jwtSecret;
    }


    public DatabaseCredentials getSecret() {

        String secretName = "omz-env-secrets-backend";

        GetSecretValueRequest getSecretValueRequest = GetSecretValueRequest.builder()
                .secretId(secretName)
                .build();

        GetSecretValueResponse getSecretValueResponse;

        try {
            getSecretValueResponse = client.getSecretValue(getSecretValueRequest);
        } catch (Exception e) {
            throw e;
        }

        String secret = getSecretValueResponse.secretString();

        ObjectMapper mapper = new ObjectMapper();
        DatabaseCredentials credentials;
        try {
            JsonNode node = mapper.readTree(secret);
            credentials = new DatabaseCredentials(
                    node.get("DB_CLUSTER_URL").asText(),
//                    node.get("DB_URL").asText(),
                    node.get("DB_USER").asText(),
                    node.get("DB_PASSWORD").asText()
            );
        } catch (Exception e) {
            throw new RuntimeException("DB secret JSON 구문 분석에 실패했습니다.", e);
        }

        return credentials;
    }
}