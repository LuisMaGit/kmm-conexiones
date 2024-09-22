package com.luisma.conexiones.services

import com.luisma.conexiones.models.game.GameDistribution
import com.luisma.conexiones.models.game.GameDistributionCoordinates
import com.luisma.conexiones.models.game.GameTile
import com.luisma.conexiones.services.game.GameDBMappersService
import com.luisma.conexiones.services.mocks.stringUtilsMockService
import com.luisma.conexiones.services.mocks.userProfileMockService
import kotlin.test.Test
import kotlin.test.assertEquals

class GameDBMappersServiceTest {

    private fun getService(
        strUtilsService: IStringUtilsService = stringUtilsMockService(),
        userProfileService: IUserProfileService = userProfileMockService()
    ): GameDBMappersService {
        return GameDBMappersService(
            strUtilsService = strUtilsService,
            userProfileService = userProfileService
        )
    }


    @Test
    fun `decodeGameTiles - test multiple cases`() {
        val service = getService(strUtilsService = stringUtilsService())
        val json = """
            [{"words":"MASS,SEA,SLEW,TON","description":"LARGE AMOUNT","difficulty":1},{"words":"BUCKLE,CAVE,COLLAPSE,GIVE","description":"FALL IN UNDER PRESSURE","difficulty":2}]
            """
        val tiles = listOf(
            GameTile(
                words = listOf("MASS", "SEA", "SLEW", "TON"),
                description = "LARGE AMOUNT",
                difficulty = 1,
                wordsJoined = "MASS, SEA, SLEW, TON"
            ),
            GameTile(
                words = listOf("BUCKLE", "CAVE", "COLLAPSE", "GIVE"),
                description = "FALL IN UNDER PRESSURE",
                difficulty = 2,
                wordsJoined = "BUCKLE, CAVE, COLLAPSE, GIVE"
            )
        )
        assertEquals(tiles, service.decodeGameTiles(json = json))
    }

    @Test
    fun `decodeGameDistribution - test multiple cases`() {
        val service = getService()
        val json = """
            [{"should":{"col":0,"row":0},"is":{"col":2,"row":1}},{"should":{"col":1,"row":0},"is":{"col":3,"row":2}}]
            """
        val distribution = listOf(
            GameDistribution(
                should = GameDistributionCoordinates(column = 0, row = 0),
                its = GameDistributionCoordinates(column = 2, row = 1)
            ),
            GameDistribution(
                should = GameDistributionCoordinates(column = 1, row = 0),
                its = GameDistributionCoordinates(column = 3, row = 2)
            ),
        )
        assertEquals(distribution, service.decodeGameDistribution(json = json))
        assertEquals(emptyList(), service.decodeGameDistribution(json = ""))
        assertEquals(emptyList(), service.decodeGameDistribution(json = null))
    }

    @Test
    fun `encodeGameDistribution - test multiple cases`() {
        val service = getService()
        val expected = """[{"should":{"col":0,"row":0},"is":{"col":2,"row":1}},{"should":{"col":1,"row":0},"is":{"col":3,"row":2}}]"""
        val distribution = listOf(
            GameDistribution(
                should = GameDistributionCoordinates(column = 0, row = 0),
                its = GameDistributionCoordinates(column = 2, row = 1)
            ),
            GameDistribution(
                should = GameDistributionCoordinates(column = 1, row = 0),
                its = GameDistributionCoordinates(column = 3, row = 2)
            ),
        )
        assertEquals(expected, service.encodeGameDistribution(distribution))
    }
}