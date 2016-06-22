package parsers

import scala.util.parsing.combinator._

class SimpleParser extends RegexParsers {

  def word: Parser[String] = """[a-z]+""".r ^^ { _.toString }

  def number: Parser[Int] = """(0|[1-9]\d*)""".r ^^ { _.toInt }

  def freq: Parser[WordFreq] = word ~ number ^^ { case wd ~ fr => WordFreq(wd,fr) }

}

case class WordFreq(word: String, count: Int) {

  override def toString = "Word <" + word + "> " + "occurs with frequency " + count

}

object MyParser extends SimpleParser {
  def main(args: Array[String]): Unit = {
    parse(freq*, "soso 1123 ddf 564") match {
      case Success(matched, _) => println(matched)
      case Failure(msg, _) => println("FAILURE: " + msg)
      case Error(msg, _) => println("ERROR: " + msg)
    }
  }
}
