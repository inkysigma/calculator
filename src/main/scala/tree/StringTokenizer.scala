package tree

import mapping.OperatorMapping

import scala.util.control.Breaks.{break, breakable}


class TokenizerError(val character: Char, val index: Integer, val expected: String)
  extends Error(s"A string failed to parse at character $character at index $index. Expected $expected") {
}


class StringTokenizer(val str: String) {
  private val ch = str.toCharArray
  private var index = 0


  def peekToken(): Integer = {
    if (Character.isLetter(ch(index))) {
      return TokenType.Character
    }
    else if (Character.isDigit(ch(index))) {
      return TokenType.Digit
    }
    else if (ch(index) == '(' || ch(index) == ')') {
      return TokenType.Parenthesis
    }
    else if (OperatorMapping.MAPPING.contains(String.valueOf(ch(index)))) {
      return TokenType.Operator
    }
    else if (ch(index) == ' ') {
      index = index + 1
      return peekToken()
    }
    TokenType.Error
  }

  def eos(): Boolean = {
    index == ch.length
  }

  def getToken: Character = {
    index = index + 1
    ch(index - 1)
  }


  def nextString(): String = {
    if (!Character.isLetter(ch(index)))
      return ""
    var node = GlobalMapping.globalMapping.getNode(ch(index).toString) match {
      case Some(c) => c
      case None => return getToken.toString
    }

    var lastMarked: TrieNode = TrieNode.clone(node)
    lastMarked.word = ch(index).toString

    var tempIdx = index + 1
    index = index + 1

    breakable {
      while (!eos() && tempIdx < ch.length && Character.isLetter(ch(tempIdx))) {
        if (node.marked) {
          lastMarked = node
          index = tempIdx
        }
        node.get(ch(tempIdx)) match {
          case Some(c) => node = c
          case None => break
        }
        tempIdx = tempIdx + 1
      }
    }
    lastMarked.word
  }

  def nextNumeral(): Double = {
    if (Character.isLetter(ch(index)))
      return Double.NaN

    var numeral = 0.0d
    var fractional = false
    var mantissa = 0

    while (!eos() && (Character.isDigit(ch(index)) || ch(index) == '.')) {
      if (ch(index) == '.') {
        fractional = true
      } else if (!fractional) {
        numeral = numeral * 10
        numeral += Character.digit(ch(index), 10)
      } else {
        numeral = numeral * 10
        numeral += Character.digit(ch(index), 10)
        mantissa += 1
      }
      index += 1
    }
    numeral / Math.pow(10, mantissa)
  }

  def nextOperator(): String = {
    if (Character.isDigit(ch(index)) && Character.isAlphabetic(ch(index)))
      throw new TokenizerError(ch(index), index, "operator")
    if (ch(index) == ' ')
      throw new TokenizerError(ch(index), index, "operator")
    getToken.toString
  }
}
