package tree

import drawing.Point
import mapping.{ConstantMapping, NodeMapping, PrecedenceMapping, VariableMapping}

import scala.collection.mutable
import scala.util.control.Breaks._

class ExpressionParseError(val str: String) extends Error(f"Parsing failed with $str") {

}


class ExpressionTree {

  var root: ExpressionNode = new ConstantNode(0)

  var left: Double = -10
  var right: Double = 10
  var resolution: Double = 0.1

  def setResolution(resolution: Double): Unit = {
    this.resolution = resolution
  }

  def getResolution: Double = {
    resolution
  }

  /**
    * Parses a string into an expression tree
    *
    * @param str the string to parse
    */
  def parseString(str: String): Unit = {
    val outputStack = mutable.Stack[String]()
    val operatorStack = mutable.Stack[String]()
    val tokenizer = new StringTokenizer(str)
    while (!tokenizer.eos) {
      tokenizer.peekToken() match {
        case TokenType.Character => {
          val str = tokenizer.nextString()
          if (ConstantMapping.MAPPING.contains(str))
            outputStack.push(str)
          else if (VariableMapping.MAPPING.contains(str))
            outputStack.push(str)
          breakable {
            while (operatorStack.nonEmpty) {
              if (PrecedenceMapping.getPrecedence(operatorStack.head) < PrecedenceMapping.getPrecedence(str)) {
                outputStack.push(operatorStack.pop())
              } else {
                outputStack.push(str)
                break
              }
            }
          }
        }
        case TokenType.Digit => {
          val num = tokenizer.nextNumeral()
          outputStack.push(num.toString)
        }
        case TokenType.Parenthesis => {
          val parentheses = tokenizer.nextOperator()
          if (parentheses == "(") {
            operatorStack.push(parentheses)
          } else if (parentheses == ")") {
            breakable {
              while (operatorStack.nonEmpty) {
                if (PrecedenceMapping.getPrecedence(str) >= PrecedenceMapping.getPrecedence(operatorStack.head)) {
                  outputStack.push(operatorStack.pop())
                } else {
                  break
                }
              }
            }
          } else {
            throw new ExpressionParseError(parentheses)
          }
        }
        case TokenType.Operator => {
          val operator = tokenizer.nextOperator()
          if (PrecedenceMapping.getPrecedence(operator) > PrecedenceMapping.get)
        }
        case TokenType.Error => throw new NotImplementedError()
      }
    }
  }

  def evaluate(start: Double, end: Double): Seq[Point] = {
    val points = mutable.ListBuffer[Point]()
    val map = mutable.HashMap[String, Double]()
    var i = 0
    while (this.left + i * resolution <= this.right) {
      map("x") = this.left + i * resolution
      points += new Point(this.left + i * resolution, root.evaluate(map))
      i = i + 1
    }
    points.seq
  }
}
