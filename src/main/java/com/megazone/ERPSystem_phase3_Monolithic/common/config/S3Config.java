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


            // 생성된 S3Client 반환
            S3Client s3Client = S3Client.builder()
                    .region(Region.of(region))
                    .credentialsProvider(StaticCredentialsProvider.create(
                            AwsBasicCredentials.create(accessKey, secretKey)))
                    .build();


            // 파일 리스트 가져오기 또는 파일 업로드하여 버킷에 접근 확인
            listFilesInBucket(s3Client, bucketName);
            uploadFileToBucket(s3Client, bucketName);

            return s3Client;

        } catch (Exception e) {
            log.error("S3 자격 증명 파싱에 실패했습니다.", e);

            throw new RuntimeException("S3 자격 증명 파싱에 실패했습니다.", e);
        }


    }

    // 버킷에 있는 파일 목록을 가져오는 메서드
    private void listFilesInBucket(S3Client s3Client, String bucketName) {
        try {
            ListObjectsV2Request listObjects = ListObjectsV2Request.builder()
                    .bucket(bucketName)
                    .build();

            ListObjectsV2Response response = s3Client.listObjectsV2(listObjects);
            if (response.contents().isEmpty()) {
                log.info("버킷 '{}'에 파일이 없습니다.", bucketName);
            } else {
                for (S3Object s3Object : response.contents()) {
                    log.info("버킷 '{}'에 있는 파일: {}", bucketName, s3Object.key());
                }
            }
        } catch (Exception e) {
            log.error("버킷 '{}'의 파일 목록을 가져오는 데 실패했습니다.", bucketName, e);
        }
    }

    // 버킷에 파일을 업로드하는 메서드
    private void uploadFileToBucket(S3Client s3Client, String bucketName) {
        try {
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key("sample-file.txt")
                    .build();

            s3Client.putObject(putObjectRequest, Paths.get("sample-file.txt"));
            log.info("파일 'sample-file.txt'를 버킷 '{}'에 업로드했습니다.", bucketName);
        } catch (Exception e) {
            log.error("버킷 '{}'에 파일을 업로드하는 데 실패했습니다.", bucketName, e);
        }
    }
}