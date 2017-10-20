package tree

import scala.collection.mutable

object TrieNode {
  def create(word: String): TrieNode = {
    val n: TrieNode = new TrieNode()
    n.word = word
    n
  }
}

class TrieNode {
  val subtree: mutable.HashMap[Character, TrieNode] =
    mutable.HashMap[Character, TrieNode]()
  var marked = false
  var word = ""

  def get(ch: Char): Option[TrieNode] = {
    if (subtree.contains(ch))
      return subtree.get(ch)
    None
  }

  def get(chs: Array[Char], idx: Integer): TrieNode = {
    if (chs.length < 0) {
      throw new IndexOutOfBoundsException
    }
    if (idx == chs.length - 1) {
      return this
    }
    subtree.get(chs(idx)) match {
      case Some(n) => n.get(chs, idx + 1)
      case None => {
        subtree.put(chs(idx), new TrieNode)
        subtree(chs(idx)).get(chs, idx + 1)
      }
    }
  }

  def add(chs: Array[Char], idx: Integer): Unit = {
    if (idx >= chs.length - 1) {
      marked = true
      word = new String(chs)
    } else {
      subtree.get(chs(idx)) match {
        case Some(n) => n.add(chs, idx + 1)
        case _ => {
          val node = new TrieNode()
          subtree.put(chs(idx), node)
          node.add(chs, idx + 1)
        }
      }
    }
  }

  override def equals(obj: scala.Any): Boolean = {
    obj match {
      case d: TrieNode => d.word == word && word != ""
      case _ => false
    }
  }
}