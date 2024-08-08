package com.luisma.conexiones.services

import com.luisma.conexiones.models.GameTile
import com.luisma.conexiones.services.mocks.getStringUtilsMockService
import kotlin.test.Test
import kotlin.test.assertEquals

class GameDBMappersServiceTest {


    private fun getService(
        strUtilsService: IStringUtilsService = getStringUtilsMockService(),
        userProfileService: UserProfileService = userProfileService()
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
            ),
            GameTile(
                words = listOf("BUCKLE", "CAVE", "COLLAPSE", "GIVE"),
                description = "FALL IN UNDER PRESSURE",
                difficulty = 2,
            )
        )
        assertEquals(tiles, service.decodeGameTiles(json = json))
    }
}