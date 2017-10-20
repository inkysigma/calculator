package mapping

import tree.ExpressionNode

import scala.collection.immutable.HashMap
import scala.collection.mutable
import tree.VariableNode

object VariableMapping {
  val MAPPING: mutable.HashMap[String, ExpressionNode] = mutable.HashMap[String, ExpressionNode](
    "x" -> new VariableNode("x"),
    "y" -> new VariableNode("y"),
    "z" -> new VariableNode("z"))
}