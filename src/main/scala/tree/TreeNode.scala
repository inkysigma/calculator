package tree

import mapping.DerivativeMapping

import scala.collection.mutable

sealed trait ExpressionType

case object Operator extends ExpressionType

case object Constant extends ExpressionType

case object Function extends ExpressionType

case object Variable extends ExpressionType

case object Error extends ExpressionType

trait ExpressionNode {
  var expressionType: ExpressionType = Error

  def evaluate(feedDictionary: mutable.Map[String, Double]): Double

  def derivative(feedDictionary: mutable.Map[String, Double]): Double

  def symbolicDerivative(): ExpressionNode
}

trait OperatorNode extends ExpressionNode {
  this.expressionType = Operator

  var left: ExpressionNode = ErrorNode
  var right: ExpressionNode = ErrorNode

  def setLeft(left: ExpressionNode): Unit = {
    this.left = left
  }

  def setRight(right: ExpressionNode): Unit = {
    this.right = right
  }
}

object ErrorNode extends ExpressionNode {
  override def evaluate(feedDictionary: mutable.Map[String, Double]): Double = {
    throw new NotImplementedError()
  }

  override def derivative(feedDictionary: mutable.Map[String, Double]): Double = {
    throw new NotImplementedError()
  }

  override def symbolicDerivative(): ExpressionNode = {
    throw new NotImplementedError()
  }
}

class MultiplyNode extends OperatorNode {
  def evaluate(feedDictionary: mutable.Map[String, Double]): Double = {
    left.evaluate(feedDictionary) * right.evaluate(feedDictionary)
  }

  override def derivative(feedDictionary: mutable.Map[String, Double]): Double = {
    left.derivative(feedDictionary) * right.evaluate(feedDictionary) +
      left.evaluate(feedDictionary) * right.derivative(feedDictionary)
  }

  override def symbolicDerivative(): ExpressionNode = {
    val node = new AddNode
    val nodeLeft = new MultiplyNode
    nodeLeft.setLeft(left)
    nodeLeft.setRight(right.symbolicDerivative())
    val nodeRight = new MultiplyNode
    nodeRight.setLeft(left.symbolicDerivative())
    nodeRight.setRight(right)
    node.setLeft(nodeLeft)
    node.setRight(nodeRight)
    node
  }
}

class AddNode extends OperatorNode {
  override def evaluate(feedDictionary: mutable.Map[String, Double]): Double = {
    left.evaluate(feedDictionary) + right.evaluate(feedDictionary)
  }

  override def derivative(feedDictionary: mutable.Map[String, Double]): Double = {
    left.derivative(feedDictionary) + right.derivative(feedDictionary)
  }

  override def symbolicDerivative(): ExpressionNode = {
    val node = new AddNode()
    node.setLeft(left.symbolicDerivative())
    node.setRight(right.symbolicDerivative())
    node
  }
}

class SubtractNode extends OperatorNode {
  override def evaluate(feedDictionary: mutable.Map[String, Double]): Double = {
    left.evaluate(feedDictionary) - right.evaluate(feedDictionary)
  }

  override def derivative(feedDictionary: mutable.Map[String, Double]): Double = {
    left.derivative(feedDictionary) - right.derivative(feedDictionary)
  }

  override def symbolicDerivative(): ExpressionNode = {
    val node = new SubtractNode
    node.setLeft(left.symbolicDerivative())
    node.setRight(right.symbolicDerivative())
    node
  }
}

class DivideNode extends OperatorNode {
  override def evaluate(feedDictionary: mutable.Map[String, Double]): Double = {
    left.evaluate(feedDictionary) / right.evaluate(feedDictionary)
  }

  override def derivative(feedDictionary: mutable.Map[String, Double]): Double = {
    (left.evaluate(feedDictionary) * right.derivative(feedDictionary) -
      left.derivative(feedDictionary) * right.derivative(feedDictionary)) / Math.pow(right.derivative(feedDictionary), 2)
  }

  override def symbolicDerivative(): ExpressionNode = ???
}

class PowerNode extends ExpressionNode {
  this.expressionType = Operator

  var expr: ExpressionNode = ErrorNode
  var right: Double = 0.0

  def setLeft(left: ExpressionNode): Unit = {
    this.expr = left
  }

  def setPower(right: Double): Unit = {
    this.right = right
  }

  override def evaluate(feedDictionary: mutable.Map[String, Double]): Double = {
    Math.pow(expr.evaluate(feedDictionary), right)
  }

  override def derivative(feedDictionary: mutable.Map[String, Double]): Double = {
    right * expr.derivative(feedDictionary) *
      Math.pow(expr.evaluate(feedDictionary), right - 1)
  }

  override def symbolicDerivative(): ExpressionNode = {
    val multiplyNode = new MultiplyNode
    multiplyNode.setLeft(new ConstantNode(right))
    multiplyNode.setRight(expr.symbolicDerivative())

    val secondNode = new MultiplyNode
    secondNode.setLeft(multiplyNode)
    val powerNode = new PowerNode
    powerNode.setLeft(expr)
    powerNode.setPower(right - 1)
    secondNode.setRight(powerNode)
    secondNode
  }
}

class VariableNode(val name: String) extends ExpressionNode {
  this.expressionType = Variable

  override def evaluate(feedDictionary: mutable.Map[String, Double]): Double = {
    feedDictionary.get(this.name) match {
      case Some(value) =>
        value
      case None =>
        throw new UninitializedError()
    }
  }

  override def derivative(feedDictionary: mutable.Map[String, Double]): Double = {
    1
  }

  override def symbolicDerivative(): ExpressionNode = {
    new ConstantNode(1)
  }
}

class ConstantNode(val value: Double) extends ExpressionNode {
  this.expressionType = Constant

  override def evaluate(feedDictionary: mutable.Map[String, Double]): Double = {
    value
  }

  override def derivative(feedDictionary: mutable.Map[String, Double]): Double = {
    0.0
  }

  override def symbolicDerivative(): ExpressionNode = {
    new ConstantNode(0)
  }
}


class Func[A, B](val id: String, f: Function[A, B]) {
  override def equals(obj: scala.Any): Boolean = {
    obj match {
      case a: Func[A, B] => if (a.id == this.id && a.id != "ERROR") true else false
      case _ => false
    }
  }

  def apply(v1: A): B = {
    f.apply(v1)
  }
}


class FunctionNode(function: Func[Double, Double]) extends ExpressionNode {
  this.expressionType = Function

  var innerNode: ExpressionNode = ErrorNode

  def setInnerNode(node: ExpressionNode): Unit = {
    this.innerNode = node
  }

  override def evaluate(feedDictionary: mutable.Map[String, Double]): Double = {
    function.apply(innerNode.evaluate(feedDictionary))
  }

  override def derivative(feedDictionary: mutable.Map[String, Double]): Double = {
    DerivativeMapping.MAPPING.get(function.id) match {
      case Some(d) => {
        d.evaluate(feedDictionary)
      }
      case None => throw new NotImplementedError
    }
  }

  override def symbolicDerivative(): ExpressionNode = {
    DerivativeMapping.MAPPING.get(function.id) match {
      case Some(d) => {
        val node = new MultiplyNode
        node.setLeft(innerNode.symbolicDerivative())
        d.setInnerNode(innerNode)
        node.setRight(d)
        node
      }
      case None => throw new NotImplementedError()
    }
  }
}