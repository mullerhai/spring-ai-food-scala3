package example

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class RecipeFinderApplication
object RecipeFinderApplication {
  def main(args: Array[String]): Unit = {
    System.setProperty("spring.devtools.restart.enabled", "false")
    SpringApplication.run(classOf[RecipeFinderApplication], args*)
  }
}