package com.luisma.conexiones.services

import kotlin.test.Test
import kotlin.test.assertEquals

class PaginationServiceTest {

    private fun getService(
        gameLevelsInPage: Int = 20
    ): PaginationService {
        return PaginationService(
            gameLevelsInPage = gameLevelsInPage
        )
    }

    @Test
    fun `getPageByRowId - test some cases`() {
        assertEquals(2, getService().getPageByRowId(rowId = 40))
        assertEquals(1, getService().getPageByRowId(rowId = 19))
        assertEquals(13, getService().getPageByRowId(rowId = 257))
    }

    @Test
    fun `getPageState - test some cases`() {
        assertEquals(PageState.First, getService().getPageState(page = 1, itemsInPage = 20))
        assertEquals(PageState.Last, getService().getPageState(page = 2, itemsInPage = 10))
        assertEquals(PageState.InBetween, getService().getPageState(page = 2, itemsInPage = 20))
        assertEquals(PageState.FirstAndLast, getService().getPageState(page = 1, itemsInPage = 10))
    }

    @Test
    fun `getPlayingPageIdxScrollOffset - test some cases`() {
        assertEquals(4, getService().getPlayingPageIdxScrollOffset(rowId = 5))
        assertEquals(16, getService().getPlayingPageIdxScrollOffset(rowId = 257))
        assertEquals(0, getService().getPlayingPageIdxScrollOffset(rowId = 301))
    }

    @Test
    fun `getPreviousPage - test some cases`() {
        assertEquals(setOf(4, 5, 6, 7, 8, 9), getService().getPreviousPage(setOf(5, 6, 7, 8, 9)))
        assertEquals(setOf(1, 5, 6, 7, 8, 9), getService().getPreviousPage(setOf(1, 5, 6, 7, 8, 9)))
    }

    @Test
    fun `getNextPage - test some cases`() {
        assertEquals(setOf(5, 6, 7, 8, 9, 10), getService().getNextPage(setOf(5, 6, 7, 8, 9)))
    }

}

