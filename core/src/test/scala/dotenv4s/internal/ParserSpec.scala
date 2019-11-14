package dotenv4s.internal

import org.scalatest.{Matchers, WordSpec}

class ParserSpec extends WordSpec with Matchers {
  "The plugin parser" should {

    "not accept empty lines" in {
      Parser.parse("") should equal(Map())
    }

    "not accept numeric variable names" in {
      Parser.parse("1234=5678") should equal(Map())
    }

    "not accept lines with no assignment" in {
      Parser.parse("F") should equal(Map())
    }

    "accept empty variables" in {
      Parser.parse("EMPTY=\nONE=TWO") should equal(
        Map("EMPTY" -> "", "ONE" -> "TWO")
      )
    }

    "accept unquoted strings containing whitespace" in {
      Parser.parse("SOMETHING=I love kittens") should equal(
        Map("SOMETHING" -> "I love kittens")
      )
    }

    "accept lines with trailing comments" in {
      Parser.parse("WITHOUT_COMMENT=ThisIsValue # here is a comment") should equal(
        Map("WITHOUT_COMMENT" -> "ThisIsValue")
      )
    }

    "accept lines with URLs containing # characters" in {
      Parser.parse("WITH_HASH_URL='http://example.com#awesome-id'") should equal(
        Map("WITH_HASH_URL" -> "http://example.com#awesome-id")
      )
    }

    "accept lines with quoted variables and strips quotes" in {
      Parser.parse("FOO='a=b==ccddd'") should equal(Map("FOO" -> "a=b==ccddd"))
      Parser.parse("FOO=\"blah # blah \r blah \n blah \"") should equal(
        Map("FOO" -> "blah # blah \r blah \n blah ")
      )
    }

    "accept lines with whitespace around assignment operator" in {
      Parser.parse("FOO   =   boo") should equal(Map("FOO" -> "boo"))
    }

    "accept lines with escaped characters and unescape them" in {
      Parser.parse("FOO=' \\\' \\\' '") should equal(Map("FOO" -> " \' \' "))
    }

    "accept lines with leading whitespace before variable name" in {
      Parser.parse("   FOO=noo") should equal(Map("FOO" -> "noo"))
    }

    "accept lines with leading export and ignore the export" in {
      Parser.parse(" export FOO=noo") should equal(Map("FOO" -> "noo"))
    }

    "accept lines with variables containing undescores, periods, and hyphens" in {
      Parser.parse(" export F.OO=period") should equal(Map("F.OO" -> "period"))
      Parser.parse("FO-O=hyphen") should equal(Map("FO-O" -> "hyphen"))
      Parser.parse("FOO__ = underscore") should equal(
        Map("FOO__" -> "underscore")
      )
    }

    "accept multi-line variables" in {
      val content = """MY_CERT="-----BEGIN CERTIFICATE-----
                      |123456789qwertyuiopasdfghjklzxcvbnm
                      |-----END CERTIFICATE-----
                      |"
                    """.stripMargin
      Parser.parse(content) should equal(
        Map("MY_CERT" -> """-----BEGIN CERTIFICATE-----
                                                               |123456789qwertyuiopasdfghjklzxcvbnm
                                                               |-----END CERTIFICATE-----
                                                               |""".stripMargin)
      )
    }

    "validate correct lines in a .env file" in {
      Parser.parse("FOO=bar") should equal(Map("FOO" -> "bar"))

      Parser.parse("FOO=1234") should equal(Map("FOO" -> "1234"))

      Parser.parse("COVERALLS_REPO_TOKEN=NTHnTHSNthnTHSntNt09aoesNTH6") should equal(
        Map("COVERALLS_REPO_TOKEN" -> "NTHnTHSNthnTHSntNt09aoesNTH6")
      )
    }
  }
}
