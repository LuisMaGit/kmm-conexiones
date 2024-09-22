package com.luisma.conexiones.models.game

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient


@Serializable
data class GameDistribution(
    @SerialName("should")
    val should: GameDistributionCoordinates,
    @SerialName("is")
    val its: GameDistributionCoordinates,
    @Transient
    val selected: Boolean = false
)

@Serializable
data class GameDistributionCoordinates(
    @SerialName("col")
    val column: Int,
    @SerialName("row")
    val row: Int
)
