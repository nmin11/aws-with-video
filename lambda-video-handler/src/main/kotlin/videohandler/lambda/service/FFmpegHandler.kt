package videohandler.lambda.service

import net.bramp.ffmpeg.FFmpeg
import net.bramp.ffmpeg.FFmpegExecutor
import net.bramp.ffmpeg.FFprobe
import net.bramp.ffmpeg.builder.FFmpegBuilder
import java.io.IOException


class FFmpegHandler {
  private val ffmpeg = FFmpeg("/opt/ffmpeg/ffmpeg")
  private val ffprobe = FFprobe("/opt/ffmpeg/ffprobe")

  fun getInfo(filePath: String): String {
    try {
      return ffprobe.probe(filePath).toString()
    } catch (e: IOException) {
      e.printStackTrace()
    }
    return "Error occurred"
  }

  fun createThumbnail(videoPath: String, thumbnailPath: String) {
    val builder = FFmpegBuilder()
      .overrideOutputFiles(true)
      .setInput(videoPath)
      .addExtraArgs("-ss", "00:00:05")
      .addOutput(thumbnailPath)
      .setFrames(1)
      .done()
    val executor = FFmpegExecutor(ffmpeg, ffprobe)
    executor.createJob(builder).run()
  }
}