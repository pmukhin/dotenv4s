package dotenv4s.internal

private[dotenv4s] object Loader {
  import java.nio.file.Files
  import java.nio.file.{Path, Paths}
  import scala.jdk.CollectionConverters._

  def load(path: Path, filename: String): Unit = {
    val filePath = Paths.get(path.getFileName + "/" + filename)
    val contents = Files.readAllLines(filePath).toArray.mkString("\n")
    val env = Parser.parse(contents)
    val sysEnv = System.getenv.asScala
    val clearedEnv = env.filter { case (key, _) => sysEnv.get(key).isDefined }
    val mapAsJava = clearedEnv.asJava

    NativeEnvironmentManager.setEnv(mapAsJava)
    DirtyEnvHack.setEnv(mapAsJava)
  }
}
