package tree

import mapping.{ConstantMapping, NodeMapping, VariableMapping}

import scala.collection.mutable

class Trie {
  val roots : mutable.HashMap[Char, TrieNode] = mutable.HashMap[Char, TrieNode]()

  def addWord(word: String): Unit = {
    val chars = word.toCharArray
    if (!roots.contains(chars(0)))
      roots.put(chars(0), new TrieNode)
    roots(chars(0)).add(word.toCharArray, 1)
  }

  def exists(word: String): Boolean = {
    val chars = word.toCharArray
    if (!roots.contains(chars(0)))
      return false
    roots(chars(0)).exists(word.toCharArray, 1)
  }

  def getNode(word: String): Option[TrieNode] = {
    val chars = word.toCharArray
    if (!roots.contains(chars(0)))
      return None
    roots(chars(0)).get(word.toCharArray, 1)
  }
}


object GlobalMapping {
  val globalMapping: Trie = new Trie()
  for ((name, _) <- NodeMapping.MAPPING) {
    globalMapping.addWord(name)
  }
  for ((variable, _) <- VariableMapping.MAPPING) {
    globalMapping.addWord(variable)
  }
  for ((constant, _) <- ConstantMapping.MAPPING) {
    globalMapping.addWord(constant)
  }
}
