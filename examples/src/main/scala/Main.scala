object Main {
  def main(args: Array[String]): Unit = {
    // just load the env
    dotenv4s.load()

    // and then use
    println(System.getenv("DOTENV4S_GREETING"))
  }
}
