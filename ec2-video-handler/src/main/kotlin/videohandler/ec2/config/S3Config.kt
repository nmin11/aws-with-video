package videohandler.ec2.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials
import software.amazon.awssdk.auth.credentials.AwsCredentials
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.presigner.S3Presigner

@Configuration
class S3Config {
  @Value("\${aws.credentials.access-key-id}")
  lateinit var accessKeyId: String

  @Value("\${aws.credentials.secret-access-key}")
  lateinit var secretAccessKey: String

  @Bean
  fun basicAWSCredentials(): AwsCredentials {
    return AwsBasicCredentials.create(accessKeyId, secretAccessKey)
  }

  @Bean
  fun s3Client(awsCredentials: AwsCredentials): S3Client {
    return S3Client.builder()
      .region(Region.AP_NORTHEAST_2)
      .credentialsProvider(StaticCredentialsProvider.create(awsCredentials))
      .build()
  }

  @Bean
  fun s3PreSigner(awsCredentials: AwsCredentials): S3Presigner {
    return S3Presigner.builder()
      .region(Region.AP_NORTHEAST_2)
      .credentialsProvider(StaticCredentialsProvider.create(awsCredentials))
      .build()
  }
}