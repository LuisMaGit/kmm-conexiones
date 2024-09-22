package com.luisma.conexiones.services


interface INumbUtilsService {
    fun unsortedListWithSize(size: Int): List<Int>
}
class NumbUtilsService : INumbUtilsService {
    override fun unsortedListWithSize(size: Int): List<Int> {
        val shuffled = List(size = size) { it }.shuffled()
        return shuffled
    }
}