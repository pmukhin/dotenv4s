import java.nio.file.{Path, Paths}

import dotenv4s.internal.Loader

package object dotenv4s {
  private val defaultName = ".env"
  private val defaultDir = "."

  def load(): Unit = Loader.load(Paths.get(defaultDir), defaultName)
  def load(filename: String): Unit =
    Loader.load(Paths.get(defaultDir), filename)
  def load(path: Path): Unit = Loader.load(path, defaultName)
  def load(path: Path, filename: String): Unit = Loader.load(path, filename)
}
