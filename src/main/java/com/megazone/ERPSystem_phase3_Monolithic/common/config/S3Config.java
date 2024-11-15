package com.megazone.ERPSystem_phase3_Monolithic.common.config;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
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
@Getter
public class S3Config {

    private static final String SECRET_NAME = "omz-env-secrets-backend";

    private final SecretManagerConfig secretManagerConfig;
    private String awsRegion;
    private String bucketName;

    @Autowired
    public S3Config(SecretManagerConfig secretManagerConfig) {
        this.secretManagerConfig = secretManagerConfig;
    }

    @Bean
    public S3Client s3Client() {
        String accessKey = secretManagerConfig.getSecretValueFromJson(SECRET_NAME, "AWS_ACCESS_KEY_ID");
        String secretKey = secretManagerConfig.getSecretValueFromJson(SECRET_NAME, "AWS_SECRET_ACCESS_KEY");
        this.awsRegion = secretManagerConfig.getSecretValueFromJson(SECRET_NAME, "AWS_REGION");
        this.bucketName = secretManagerConfig.getSecretValueFromJson(SECRET_NAME, "AWS_S3_BUCKET_NAME");

        return S3Client.builder()
                .region(Region.of(awsRegion))
                .credentialsProvider(StaticCredentialsProvider.create(
                        AwsBasicCredentials.create(accessKey, secretKey)))
                .build();
    }
}