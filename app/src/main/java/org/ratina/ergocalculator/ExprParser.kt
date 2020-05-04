package org.ratina.ergocalculator

import java.io.PushbackReader
import java.io.StringReader
import java.io.StringWriter

sealed class Token {
    data class Num(val num: String) : Token()
    data class Operator(val op: String) : Token()
}

class ExprParser {
    enum class TokenizeState {
        WantNumber,
        WantOp
    }

    data class TokenizeError(val msg: String)

    companion object {
        private val isDigit: (Char?) -> Boolean = { ch -> ch in '0'..'9' || ch == '.' }
        private val isOp: (Char?) -> Boolean = { ch -> when(ch) {
            '+', '-', '*', '/' -> true
            else -> false
        } }
        private val isEOF: (Char?) -> Boolean = { ch -> ch == null }

        @JvmStatic
        private fun peek(sr: PushbackReader): Char? {
            return peek(sr) { a -> a }
        }

        @JvmStatic
        private fun <T> peek(sr: PushbackReader, predicate: (Char?) -> T): T {
            var ch = sr.read()
            if (ch > -1) {
                val result = predicate(ch as Char)
                sr.unread(ch)
                return result
            }
            return predicate(null)
        }

        @JvmStatic
        private fun tokenize(input: String): List<Token> {
            val sr = PushbackReader(StringReader(input))
            var tokens = mutableListOf<Token>()
            loop@ while(true) {
                val ch = peek(sr)
                when {
                    isEOF(ch) -> break@loop
                    isDigit(ch) -> {
                        val token = Token.Num(readUntilNonNum(sr).joinToString())
                    }
                }
            }
            return tokens
            sr.close()
        }

        @JvmStatic
        private fun readUntilNonNum(sr: PushbackReader): List<Char> {
            var pbsr = PushbackReader(sr)
            var ch = pbsr.read()
            while (ch > -1) {
                var ch = ch as Char
                when (ch) {
                    in '0'..'9', '.'
                }
            }
        }
    }
}