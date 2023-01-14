package videohandler.lambda.service

import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.regions.Regions
import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.RequestHandler
import com.amazonaws.services.lambda.runtime.events.S3Event
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.AmazonS3ClientBuilder
import com.amazonaws.services.s3.model.GetObjectRequest
import com.amazonaws.services.s3.model.PutObjectRequest
import org.apache.commons.io.FileUtils
import org.apache.commons.lang3.RandomStringUtils
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.io.File
import java.io.InputStream


class S3EventHandler: RequestHandler<S3Event, String> {
  private val logger: Logger = LoggerFactory.getLogger(S3EventHandler::class.java)
  private val ffmpegHandler = FFmpegHandler()
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

    val bucketName = s3Event.records[0].s3.bucket.name
    val fileName = s3Event.records[0].s3.`object`.key

    logger.info("File: $fileName uploaded into $bucketName bucket at ${s3Event.records[0].eventTime}")

    val inputStream: InputStream
    val getObjectRequest = GetObjectRequest(bucketName, fileName)
    try {
      inputStream = s3Client.getObject(getObjectRequest).objectContent
    } catch (e: Exception) {
      e.printStackTrace()
      return "Error reading contents of the file"
    }

    val randomFileName = RandomStringUtils.randomAlphanumeric(10)
    val videoPath = "/tmp/video-$randomFileName.mp4"
    val thumbnailFilePath = "/tmp/thumbnail-$randomFileName.jpg"
    val videoFile = File(videoPath)
    FileUtils.copyInputStreamToFile(inputStream, videoFile)

    logger.info("Got video info: ${ffmpegHandler.getInfo(videoPath)}")
    ffmpegHandler.createThumbnail(videoPath, thumbnailFilePath)

    val putObjectRequest = PutObjectRequest(bucketName, "lambda/thumbnail/$randomFileName.jpg", File(thumbnailFilePath))
    try {
      s3Client.putObject(putObjectRequest)
    } catch (e: Exception) {
      e.printStackTrace()
      return "Error uploading thumbnail of the video"
    }

    // s3Client.putObject(bucketName, "lambda/thumbnail/$randomFileName.jpg", File(thumbnailFilePath))

    return null
  }
}