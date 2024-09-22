package com.luisma.conexiones.services.mocks

import com.luisma.conexiones.services.IStringUtilsService

class StringUtilsMockService : IStringUtilsService {
    override fun arrayStrToArrayInt(str: String?): List<Int> {
        TODO("Not yet implemented")
    }

    override fun separatedWordsToWordList(str: String?): List<String> {
        TODO("Not yet implemented")
    }

    override fun joinWords(words: List<String>): String {
        TODO("Not yet implemented")
    }

    override fun joinDifficulties(difficulties: Set<Int>): String {
        TODO("Not yet implemented")
    }
}

fun stringUtilsMockService() : StringUtilsMockService {
    return StringUtilsMockService()
}