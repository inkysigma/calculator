package tree

import mapping.{ConstantMapping, NodeMapping, VariableMapping}

class Trie {
  val root: TrieNode = new TrieNode()

  def addWord(word: String): Unit = {
    root.add(word.toCharArray, 0)
  }

  def exists(word: String): Boolean = {
    root.get(word.toCharArray, 0).marked
  }

  def getNode(word: String): TrieNode = {
    root.get(word.toCharArray, 0)
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
