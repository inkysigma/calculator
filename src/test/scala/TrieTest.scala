import org.scalatest.{FlatSpec, Matchers}
import tree.{TrieNode, Trie}

class TrieTest extends FlatSpec with Matchers {
  val trie = new Trie

  "Trie" should "add nodes and words" in {
    trie.addWord("hello")
    trie.addWord("hi")
    trie.addWord("bonjour")
  }

  it should "find if words exist" in {
    assert(trie.exists("hello"))
    assert(trie.exists("hi"))
    assert(trie.exists("bonjour"))
    assert(!trie.exists("not here"))
    assert(!trie.exists("help"))
    assert(!trie.exists("also not here"))
  }

  it should "get nodes that are necessary" in {
    assert(trie.getNode("hello").get.equals(TrieNode.create("hello")))
    assert(trie.getNode("hi").get.equals(TrieNode.create("hi")))
    assert(trie.getNode("bonjour").get.equals(TrieNode.create("bonjour")))
  }
}
