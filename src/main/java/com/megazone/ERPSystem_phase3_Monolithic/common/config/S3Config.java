package com.megazone.ERPSystem_phase3_Monolithic.common.config;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Request;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Response;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Object;
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueRequest;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueResponse;

import java.nio.file.Paths;

@Slf4j
@Configuration
public class S3Config {

    private static final String SECRET_NAME = "omz-env-secrets-backend";

    @Bean
    public S3Client s3Client() {
        String secretString = getSecretFromSecretsManager(SECRET_NAME);
        return buildS3Client(secretString);
    }

    private String getSecretFromSecretsManager(String secretName) {
        SecretsManagerClient client = SecretsManagerClient.builder().region(Region.of("ap-northeast-2")).build();
        GetSecretValueRequest request = GetSecretValueRequest.builder()
                .secretId(secretName)
                .build();
        GetSecretValueResponse response = client.getSecretValue(request);
        return response.secretString();
    }

    private S3Client buildS3Client(String secretString) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode node = mapper.readTree(secretString);

            String accessKey = node.get("AWS_ACCESS_KEY_ID").asText();
            String secretKey = node.get("AWS_SECRET_ACCESS_KEY").asText();
            String region = node.get("AWS_REGION").asText();
            String bucketName = node.get("AWS_S3_BUCKET_NAME").asText();

            log.info("S3 자격 증명을 성공적으로 파싱했습니다. 버킷 이름: {}", bucketName);


            return S3Client.builder()
                    .region(Region.of(region))
                    .credentialsProvider(StaticCredentialsProvider.create(
                            AwsBasicCredentials.create(accessKey, secretKey)))
                    .build();

        } catch (Exception e) {
            throw new RuntimeException("S3 자격 증명 파싱에 실패했습니다.", e);
        }

    }

}