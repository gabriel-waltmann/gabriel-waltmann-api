package com.waltmann.waltmann_api.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3ClientBuilder;

@Configuration
public class AWSConfig {
  @Value("${aws.region}")
  private String awsRegion;
  @Value("${aws.accessKey:}")
  private String accessKeyId;
  @Value("${aws.secretKey:}")
  private String secretAccessKey;

  @Bean
  public S3Client createS3Instance() {
    S3ClientBuilder s3ClintBuilder = S3Client.builder()
        .region(Region.of(awsRegion));

    Boolean accessKeyIdValid = accessKeyId != null && !accessKeyId.isEmpty();
    Boolean secretAccessKeyValid = secretAccessKey != null && !secretAccessKey.isEmpty();

    if (accessKeyIdValid && secretAccessKeyValid) {
      AwsBasicCredentials awsCredentials = AwsBasicCredentials.create(accessKeyId, secretAccessKey);

      StaticCredentialsProvider awsProvider = StaticCredentialsProvider.create(awsCredentials);

      s3ClintBuilder.credentialsProvider(awsProvider);
    }

    return s3ClintBuilder.build();
  }
}
