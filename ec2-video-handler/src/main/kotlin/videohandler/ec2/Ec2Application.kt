package videohandler.ec2

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class Ec2Application

fun main(args: Array<String>) {
  runApplication<Ec2Application>(*args)
}
