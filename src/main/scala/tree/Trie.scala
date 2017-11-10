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
  val trie: Trie = new Trie()
  val hash: mutable.HashMap[String, ExpressionNode] = mutable.HashMap[String, ExpressionNode]()
  for ((name, node) <- NodeMapping.MAPPING) {
    trie.addWord(name)
    hash.put(name, node)
  }
  for ((variable, node) <- VariableMapping.MAPPING) {
    trie.addWord(variable)
    hash.put(variable, node)
  }
  for ((constant, node) <- ConstantMapping.MAPPING) {
    trie.addWord(constant)
    hash.put(constant, new ConstantNode(node))
  }
}
