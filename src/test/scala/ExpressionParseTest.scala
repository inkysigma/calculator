import drawing.{Point}
import org.scalatest.{FlatSpec, Matchers}
import tree.ExpressionTree

class ExpressionParseTest extends FlatSpec with Matchers {
  val expressionTree = new ExpressionTree

  "The graph" should "parse the following expressions" in {
    expressionTree.parseString("sinx")
    expressionTree.parseString("sin(x)")
  }

  it should "do nothing" in {

  }
}
