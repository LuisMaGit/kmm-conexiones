package com.luisma.conexiones.services.mocks

import com.luisma.conexiones.services.INumbUtilsService

class NumbUtilsMockService(
    val unsortedListStub: List<Int>? = null
) : INumbUtilsService {
    override fun unsortedListWithSize(size: Int): List<Int> {
        return unsortedListStub ?: emptyList()
    }
}

fun gameNumbUtilsMockService(
    unsortedListStub: List<Int>? = null
): INumbUtilsService {
    return NumbUtilsMockService(
        unsortedListStub = unsortedListStub
    )
}