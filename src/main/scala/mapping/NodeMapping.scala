package mapping

import tree.{ExpressionNode, Func, FunctionNode}

import scala.collection.mutable

object NodeMapping {
  val MAPPING: mutable.HashMap[String, ExpressionNode] = mutable.HashMap[String, ExpressionNode](
    "ln" -> new FunctionNode(new Func("ln", Math.log)),
    "log" -> new FunctionNode(new Func("log", Math.log10)),
    "sqrt" -> new FunctionNode(new Func("sqrt", Math.sqrt)),
    "ceil" -> new FunctionNode(new Func("ceil", Math.ceil)),
    "abs" -> new FunctionNode(new Func("abs", Math.abs)),
    "sin" -> new FunctionNode(new Func("sin", Math.sin)),
    "cos" -> new FunctionNode(new Func("cos", Math.cos)),
    "tan" -> new FunctionNode(new Func("tan", Math.tan)),
    "asin" -> new FunctionNode(new Func("asin", Math.asin)),
    "acos" -> new FunctionNode(new Func("acos", Math.acos)),
    "atan" -> new FunctionNode(new Func("atan", Math.atan)),
    "sinh" -> new FunctionNode(new Func("sinh", Math.sinh)),
    "cosh" -> new FunctionNode(new Func("cosh", Math.cosh)),
    "tanh" -> new FunctionNode(new Func("tanh", Math.tanh))
  )
}
