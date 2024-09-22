package com.luisma.conexiones.services

import com.luisma.conexiones.contracts.LETTERS_WORDS_SEPARATOR

interface IStringUtilsService {
    fun arrayStrToArrayInt(str: String?): List<Int>
    fun separatedWordsToWordList(str: String?): List<String>
    fun joinWords(words: List<String>): String

    fun joinDifficulties(difficulties: Set<Int>): String
}

class StringUtilsService : IStringUtilsService {

    override fun arrayStrToArrayInt(str: String?): List<Int> {
        if (str.isNullOrEmpty()) {
            return emptyList()
        }
        val trimmedStr = str.trim().removeSurrounding("[", "]")
        return trimmedStr.split(LETTERS_WORDS_SEPARATOR).map { it.trim().toInt() }
    }

    override fun separatedWordsToWordList(str: String?): List<String> {
        if (str.isNullOrEmpty()) {
            return emptyList()
        }
        return str.trim().split(LETTERS_WORDS_SEPARATOR)
    }

    override fun joinWords(words: List<String>): String {
        return words.joinToString(separator = "$LETTERS_WORDS_SEPARATOR ")
    }

    override fun joinDifficulties(difficulties: Set<Int>): String {
        if (difficulties.isEmpty()) {
            return ""
        }
        val onlyNumbers = difficulties.joinToString(separator = LETTERS_WORDS_SEPARATOR)
        return "[$onlyNumbers]"
    }

}