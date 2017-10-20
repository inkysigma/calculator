package mapping

import scala.collection.mutable

object PrecedenceMapping {
  private val mapping = mutable.Map[String, Integer](
    ("(", 5),
    (")", 5),
    ("^", 4),
    ("*", 3),
    ("/", 3),
    ("-", 2),
    ("+", 2)
  )

  def getPrecedence(str: String): Int = {
    if (NodeMapping.MAPPING.contains(str)) {
      return 6
    }
    mapping(str)
  }
}
