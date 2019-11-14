package dotenv4s.internal

private[internal] object Parser {
  private val LINE_REGEX =
    """(?xms)
       (?:^|\A)           # start of line
       \s*                # leading whitespace
       (?:export\s+)?     # export (optional)
       (                  # start variable name (captured)
         [a-zA-Z_]          # single alphabetic or underscore character
         [a-zA-Z0-9_.-]*    # zero or more alphnumeric, underscore, period or hyphen
       )                  # end variable name (captured)
       (?:\s*=\s*?)       # assignment with whitespace
       (                  # start variable value (captured)
         '(?:\\'|[^'])*'    # single quoted variable
         |                  # or
         "(?:\\"|[^"])*"    # double quoted variable
         |                  # or
         [^\#\r\n]*         # unquoted variable
       )                  # end variable value (captured)
       \s*                # trailing whitespace
       (?:                # start trailing comment (optional)
         \#                 # begin comment
         (?:(?!$).)*        # any character up to end-of-line
       )?                 # end trailing comment (optional)
       (?:$|\z)           # end of line
    """.r

  def parse(contents: String): Map[String, String] =
    LINE_REGEX
      .findAllMatchIn(contents)
      .map { keyValue =>
        (keyValue.group(1), unescapeCharacters(removeQuotes(keyValue.group(2))))
      }
      .toMap

  private def unescapeCharacters(value: String): String =
    value.replaceAll("""\\([^$])""", "$1")

  private def removeQuotes(value: String): String =
    value.trim match {
      case quoted if quoted.startsWith("'") && quoted.endsWith("'") =>
        quoted.substring(1, quoted.length - 1)
      case quoted if quoted.startsWith("\"") && quoted.endsWith("\"") =>
        quoted.substring(1, quoted.length - 1)
      case unquoted => unquoted
    }

}
