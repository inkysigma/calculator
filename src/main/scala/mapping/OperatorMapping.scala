package mapping

import tree._

import scala.collection.mutable

object OperatorMapping {
  val MAPPING: mutable.HashMap[String, ExpressionNode] = mutable.HashMap(
    "+" -> new AddNode,
    "-" -> new SubtractNode,
    "*" -> new MultiplyNode,
    "/" -> new DivideNode,
    "^" -> new PowerNode
  )
}
