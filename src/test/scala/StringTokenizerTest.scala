import org.scalatest.{FlatSpec, Matchers}
import tree.{StringTokenizer, TokenType}

import scala.collection.mutable

class StringTokenizerTest extends FlatSpec with Matchers {
  "tree.StringTokenizer" should "output 4,+,4" in {
    val tokenizer = new StringTokenizer("4+4")
    var output = mutable.Seq[String]()
    val expected = mutable.Seq[String]("4.0", "+", "4.0")
    while (!tokenizer.eos()) {
      val tokenType = tokenizer.peekToken()
      if (tokenType == TokenType.Digit) {
        output = output :+ tokenizer.nextNumeral().toString
      } else if (tokenType == TokenType.Operator) {
        output = output :+ tokenizer.nextOperator()
      }
    }

    assert(output.toSet.equals(expected.toSet))
  }
}
