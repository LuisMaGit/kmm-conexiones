package com.luisma.conexiones.services

import kotlinx.datetime.Clock
import kotlin.random.Random


interface INumbUtilsService {
    fun unsortedListWithSize(size: Int): List<Int>

    fun getRandomInt(): Int
}

class NumbUtilsService : INumbUtilsService {
    override fun unsortedListWithSize(size: Int): List<Int> {
        val shuffled = List(size = size) { it }.shuffled()
        return shuffled
    }

    override fun getRandomInt(): Int {
        val now = Clock.System.now()
        return Random(seed = now.epochSeconds).nextInt()
    }
}