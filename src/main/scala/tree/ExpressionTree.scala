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
    val outputStack = List[String]()
    val operatorStack = List[String]()
    val tokenizer = new StringTokenizer(str)
    while (!tokenizer.eos()) {
      tokenizer.peekToken() match {
        case TokenType.Character =>
          val str = tokenizer.nextString()
          if (ConstantMapping.MAPPING.contains(str)) {
            outputStack +: str
          }
          else if (VariableMapping.MAPPING.contains(str)) {
            outputStack +: str
          }
          else if (operatorStack.isEmpty) {
            operatorStack +: str
          }
          else {
            var ended = false
            while (operatorStack.nonEmpty && !ended) {
              if (PrecedenceMapping.getPrecedence(operatorStack.head) >= PrecedenceMapping.getPrecedence(str)) {
                outputStack +: operatorStack.tail
              } else {
                outputStack.push(str)
                ended = true
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
          if (operatorStack.size > 0 && PrecedenceMapping.getPrecedence(operator) >=
            PrecedenceMapping.getPrecedence(operatorStack.head)) {
            outputStack.push(operatorStack.pop())
          } else {
            operatorStack.push(operator)
          }
        }
      }
    }

    while (operatorStack.nonEmpty) {
      outputStack.push(operatorStack.pop())
    }
    root = parseExpression(outputStack)

  }

  def parseDouble(s: String): Option[Double] = {
    try {
      Some(s.toDouble)
    } catch {
      case _ => None
    }
  }

  def parseExpression(output: mutable.Stack[String]) : ExpressionNode = {
    parseDouble(output.head) match {
      case Some(d) => {
        output.pop()
        return new ConstantNode(d)
      }
    }
    if (ConstantMapping.MAPPING.contains(output.head))
      return new ConstantNode(ConstantMapping.MAPPING(output.pop()))
    ErrorNode
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
