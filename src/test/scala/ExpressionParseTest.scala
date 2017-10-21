import drawing.{Point}
import org.scalatest.{FlatSpec, Matchers}
import tree.ExpressionTree

class ExpressionParseTest extends FlatSpec with Matchers {
  val expressionTree = new ExpressionTree

  "The graph" should "redraw points in a sorted order" in {
    expressionTree.parseString("sinx")
  }

  it should "do nothing" in {

  }
}
