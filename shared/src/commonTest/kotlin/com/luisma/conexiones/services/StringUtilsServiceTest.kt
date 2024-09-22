package com.luisma.conexiones.services

import kotlin.test.Test
import kotlin.test.assertEquals

class StringUtilsServiceTest {
    private fun getService(): StringUtilsService {
        return StringUtilsService()
    }

    @Test
    fun `arrayStrToArrayInt - test some cases`() {
        assertEquals(emptyList(), getService().arrayStrToArrayInt(str = ""))
        assertEquals(listOf(1,2,3,4), getService().arrayStrToArrayInt(str = "[1,2,3,4]"))
    }

    @Test
    fun `separatedWordsToWordList - test some cases`() {
        assertEquals(emptyList(), getService().separatedWordsToWordList(str = ""))
        assertEquals(listOf("aa","c","bb","dddd"), getService().separatedWordsToWordList(str = "aa,c,bb,dddd"))
    }

    @Test
    fun `joinWords - test some cases`() {
        assertEquals("aa, c, bb, dddd", getService().joinWords(listOf("aa","c","bb","dddd")))
    }

    @Test
    fun `joinDifficulties - test some cases`() {
        assertEquals("[1,2,3]", getService().joinDifficulties(setOf(1,2,3)))
        assertEquals("[1]", getService().joinDifficulties(setOf(1)))
        assertEquals("", getService().joinDifficulties(emptySet()))
    }


}