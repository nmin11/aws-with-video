package videohandler.ec2.controller

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class VideoController {
  @PostMapping("/video")
  fun uploadVideoAndThumbnail() {

  }
}