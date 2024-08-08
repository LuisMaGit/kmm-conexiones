package com.luisma.conexiones.services

import com.luisma.conexiones.contracts.GAME_AMOUNT_TRIES
import com.luisma.conexiones.models.StatsBarModel
import com.luisma.conexiones.models.StatsData
import com.luisma.conexiones.services_db.IGameDBService

class StatsService(
    private val dbService: IGameDBService,
) {

    private fun calcPercentage(
        value: Int,
        total: Int
    ): Int {
        if (total == 0) {
            return 0
        }
        return value * 100 / total
    }

    suspend fun calculateStats(): StatsData {
        val total = dbService.selectTotalGames()
        val played = dbService.selectTotalPlayedCount()
        val wins = dbService.selectWinedGamesCount()
        val lost = dbService.selectLostGamesCount()
        val winedBar = StatsBarModel(
            value = wins,
            total = played,
            percentage = calcPercentage(total = played, value = wins)
        )
        val lostBar = StatsBarModel(
            value = lost,
            total = played,
            percentage = calcPercentage(total = played, value = lost)
        )
        val playedBar = StatsBarModel(
            value = played,
            total = total,
            percentage = calcPercentage(total = total, value = played)
        )

        val distribution = mutableListOf<Int>()
        repeat(GAME_AMOUNT_TRIES) {
            val amountTries = dbService.selectGuessedTriesCount(guessedTries = it + 1)
            distribution.add(amountTries)
        }
        val maxDistribution = distribution.max()
        val maxDistributionIdx = distribution.indexOf(maxDistribution)
        val distributionBars = distribution.map { value ->
            StatsBarModel(
                value = value,
                total = maxDistribution,
                percentage = calcPercentage(value = value, total = maxDistribution)
            )
        }

        return StatsData(
            winedBar = winedBar,
            lostBar = lostBar,
            playedBar = playedBar,
            distributionBars = distributionBars,
            maxDistributionIdx = maxDistributionIdx
        )
    }


}