package com.luisma.conexiones.services

class PaginationService(
    private val gameLevelsInPage: Int
) {

    fun getPageByRowId(rowId: Int): Int {
        if (rowId <= gameLevelsInPage) {
            return 1
        }

        if (rowId % gameLevelsInPage == 0) {
            return rowId / gameLevelsInPage
        }

        return rowId / gameLevelsInPage + 1
    }

    fun getOffsetByPage(page: Int): Int {
        return (page - 1) * gameLevelsInPage
    }

    fun getPageState(itemsInPage: Int, page: Int): PageState {
        return if (page == 1 && itemsInPage < gameLevelsInPage) {
            PageState.FirstAndLast
        } else if (page == 1) {
            PageState.First
        } else if (itemsInPage < gameLevelsInPage) {
            PageState.Last
        } else {
            PageState.InBetween
        }
    }

    fun getPlayingPageIdxScrollOffset(rowId: Int): Int {
        if (rowId <= gameLevelsInPage) {
            return rowId - 1
        }

        return rowId % gameLevelsInPage - 1
    }

    fun reachFirstPageByPageState(pageState: PageState): Boolean {
        return pageState == PageState.First || pageState == PageState.FirstAndLast
    }


    fun reachLastPageByPageState(pageState: PageState): Boolean {
        return pageState == PageState.Last || pageState == PageState.FirstAndLast
    }

    fun getPreviousPage(fetchedPages: Set<Int>): Set<Int> {
        if (fetchedPages.isEmpty() || fetchedPages.contains(1)) {
            return fetchedPages
        }

        val sorted = fetchedPages.sorted()
        val output = sorted.map { it }.toMutableList()
        output.add(sorted.first() - 1)
        return output.sorted().toSet()
    }

    fun getNextPage(fetchedPages: Set<Int>): Set<Int> {
        if (fetchedPages.isEmpty()) {
            return fetchedPages
        }

        val sorted = fetchedPages.sorted()
        val output = sorted.map { it }.toMutableList()
        output.add(sorted.last() + 1)
        return output.sorted().toSet()
    }

}


enum class PageState {
    First,
    Last,
    FirstAndLast,
    InBetween,
}