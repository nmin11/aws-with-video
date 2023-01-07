package videohandler.lambda.service

import net.bramp.ffmpeg.FFmpeg
import net.bramp.ffmpeg.FFprobe
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.io.File

class FFmpegHandler {
  private val logger: Logger = LoggerFactory.getLogger(FFmpeg::class.java)

  val ffmpeg = FFmpeg("/opt/ffmpeg/ffmpeg")
  val ffprobe = FFprobe("/opt/ffmpeg/ffprove")

  fun encode(videoFile: File) {
    logger.info(videoFile.name)
    logger.info(videoFile.extension)
  }
}