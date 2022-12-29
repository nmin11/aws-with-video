package videohandler.lambda

import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.regions.Regions
import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.RequestHandler
import com.amazonaws.services.lambda.runtime.events.S3Event
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.AmazonS3ClientBuilder
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.util.StreamUtils
import java.io.IOException
import java.io.InputStream
import java.nio.charset.StandardCharsets

class S3EventHandler: RequestHandler<S3Event, String> {
  private val logger: Logger = LoggerFactory.getLogger(S3EventHandler::class.java)
  private val accessKeyId = System.getenv("ACCESS_KEY_ID")
  private val secretAccessKey = System.getenv("SECRET_ACCESS_KEY")
  private val basicAWSCredentials: BasicAWSCredentials = BasicAWSCredentials(accessKeyId, secretAccessKey)

  private val s3Client: AmazonS3 = AmazonS3ClientBuilder
    .standard()
    .withCredentials(AWSStaticCredentialsProvider(basicAWSCredentials))
    .withRegion(Regions.AP_NORTHEAST_2)
    .build()

  override fun handleRequest(s3Event: S3Event, context: Context): String? {
    logger.info("Lambda function is invoked: $s3Event")
    logger.info(accessKeyId)
    logger.info(secretAccessKey)

    val bucketName = s3Event.records[0].s3.bucket.name
    val fileName = s3Event.records[0].s3.`object`.key

    logger.info("File: $fileName uploaded into $bucketName bucket at ${s3Event.records[0].eventTime}")

    try {
      val inputStream: InputStream = s3Client.getObject(bucketName, fileName).objectContent
      logger.info("File Contents : ${StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8)}")
    } catch (e: IOException) {
      e.printStackTrace()
      return "Error reading contents of the file"
    }

    return null
  }
}