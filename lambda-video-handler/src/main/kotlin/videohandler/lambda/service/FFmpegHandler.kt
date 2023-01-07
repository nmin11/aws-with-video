package videohandler.lambda.service

import net.bramp.ffmpeg.FFmpeg
import net.bramp.ffmpeg.FFprobe
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.io.IOException

class FFmpegHandler {
  private val logger: Logger = LoggerFactory.getLogger(FFmpeg::class.java)

  private val ffmpeg = FFmpeg("/opt/ffmpeg/ffmpeg")
  private val ffprobe = FFprobe("/opt/ffmpeg/ffprobe")

  fun encode(filePath: String) {
    try {
      val ffmpegProbeResult = ffprobe.probe(filePath)
      logger.info("Probe result", ffmpegProbeResult)
    } catch (e: IOException) {
      e.printStackTrace()
    }
  }
}