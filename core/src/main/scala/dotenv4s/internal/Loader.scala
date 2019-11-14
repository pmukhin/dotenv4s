package dotenv4s.internal

private[dotenv4s] object Loader {
  import java.nio.file.Files
  import java.nio.file.{Path, Paths}
  import collection.JavaConverters._

  def load(path: Path, filename: String): Unit = {
    val filePath = Paths.get(path.getFileName + "/" + filename)
    val contents = Files.readAllLines(filePath).toArray.mkString("\n")
    val env = Parser.parse(contents)
    DirtyEnvHack.setEnv(env.asJava)
  }
}
