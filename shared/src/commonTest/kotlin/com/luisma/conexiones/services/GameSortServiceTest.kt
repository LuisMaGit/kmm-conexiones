package com.luisma.conexiones.services

import com.luisma.conexiones.models.game.GameDistribution
import com.luisma.conexiones.models.game.GameDistributionCoordinates
import com.luisma.conexiones.services.game.GameSortService
import com.luisma.conexiones.services.mocks.gameNumbUtilsMockService
import kotlin.test.Test
import kotlin.test.assertEquals

class GameSortServiceTest {

    private fun getService(
        numbUtilsService: INumbUtilsService = gameNumbUtilsMockService(),
        rows: Int = 4,
        columns: Int = 4
    ): GameSortService {
        return GameSortService(
            numbUtilsService = numbUtilsService,
            rows = rows,
            columns = columns
        )
    }

    private val mixed2x2 = listOf(
        // 0
        GameDistribution(
            should = GameDistributionCoordinates(
                column = 0,
                row = 0,
            ),
            its = GameDistributionCoordinates(
                column = 1,
                row = 0,
            )
        ),
        // 1
        GameDistribution(
            should = GameDistributionCoordinates(
                column = 1,
                row = 0,
            ),
            its = GameDistributionCoordinates(
                column = 0,
                row = 1,
            )
        ),
        // 2
        GameDistribution(
            should = GameDistributionCoordinates(
                column = 0,
                row = 1,
            ),
            its = GameDistributionCoordinates(
                column = 1,
                row = 1,
            )
        ),
        // 3
        GameDistribution(
            should = GameDistributionCoordinates(
                column = 1,
                row = 1,
            ),
            its = GameDistributionCoordinates(
                column = 0,
                row = 0,
            )
        ),
    )

    private val onlyOneMixed2x2 = listOf(
        // 0
        GameDistribution(
            should = GameDistributionCoordinates(
                column = 0,
                row = 0,
            ),
            its = GameDistributionCoordinates(
                column = 0,
                row = 0,
            )
        ),
        // 1
        GameDistribution(
            should = GameDistributionCoordinates(
                column = 0,
                row = 1,
            ),
            its = GameDistributionCoordinates(
                column = 0,
                row = 1,
            )
        ),
        // 2
        GameDistribution(
            should = GameDistributionCoordinates(
                column = 0,
                row = 1,
            ),
            its = GameDistributionCoordinates(
                column = 1,
                row = 1,
            )
        ),
        // 3
        GameDistribution(
            should = GameDistributionCoordinates(
                column = 1,
                row = 1,
            ),
            its = GameDistributionCoordinates(
                column = 0,
                row = 1,
            )
        ),
    )

    @Test
    fun `mixGameDistribution - test 2x2`() {
        val unsortedList = listOf(
            3,// col: 0, row: 0
            0,// col: 1, row: 0
            1,// col: 0, row: 1
            2 // col: 1, row: 1
        )
        val service = getService(
            numbUtilsService = gameNumbUtilsMockService(unsortedListStub = unsortedList),
            rows = 2,
            columns = 2
        )

        val result = service.mixGameDistribution(
            gameDistribution = service.fullGameListSorted(),
            columnCount = 2,
            rowCount = 2
        )

        assertEquals(result, mixed2x2)
    }

    @Test
    fun `mixGameDistribution - test 4x4`() {
        val unsortedList = listOf(
            5, 1, 3, 12, //
            9, 8, 0, 15, //
            10, 14, 4, 7, //
            11, 13, 6, 2 //
        )
        val service = getService(
            numbUtilsService = gameNumbUtilsMockService(unsortedListStub = unsortedList),
            rows = 4,
            columns = 4
        )
        val expected = listOf(
            // row 0
            GameDistribution(
                should = GameDistributionCoordinates(column = 0, row = 0),
                its = GameDistributionCoordinates(column = 2, row = 1)
            ),
            GameDistribution(
                should = GameDistributionCoordinates(column = 1, row = 0),
                its = GameDistributionCoordinates(column = 1, row = 0)
            ),
            GameDistribution(
                should = GameDistributionCoordinates(column = 2, row = 0),
                its = GameDistributionCoordinates(column = 3, row = 3)
            ),
            GameDistribution(
                should = GameDistributionCoordinates(column = 3, row = 0),
                its = GameDistributionCoordinates(column = 2, row = 0)
            ),
            // row 1
            GameDistribution(
                should = GameDistributionCoordinates(column = 0, row = 1),
                its = GameDistributionCoordinates(column = 2, row = 2)
            ),
            GameDistribution(
                should = GameDistributionCoordinates(column = 1, row = 1),
                its = GameDistributionCoordinates(column = 0, row = 0)
            ),
            GameDistribution(
                should = GameDistributionCoordinates(column = 2, row = 1),
                its = GameDistributionCoordinates(column = 2, row = 3)
            ),
            GameDistribution(
                should = GameDistributionCoordinates(column = 3, row = 1),
                its = GameDistributionCoordinates(column = 3, row = 2)
            ),
            // row 2
            GameDistribution(
                should = GameDistributionCoordinates(column = 0, row = 2),
                its = GameDistributionCoordinates(column = 1, row = 1)
            ),
            GameDistribution(
                should = GameDistributionCoordinates(column = 1, row = 2),
                its = GameDistributionCoordinates(column = 0, row = 1)
            ),
            GameDistribution(
                should = GameDistributionCoordinates(column = 2, row = 2),
                its = GameDistributionCoordinates(column = 0, row = 2)
            ),
            GameDistribution(
                should = GameDistributionCoordinates(column = 3, row = 2),
                its = GameDistributionCoordinates(column = 0, row = 3)
            ),
            // row 3
            GameDistribution(
                should = GameDistributionCoordinates(column = 0, row = 3),
                its = GameDistributionCoordinates(column = 3, row = 0)
            ),
            GameDistribution(
                should = GameDistributionCoordinates(column = 1, row = 3),
                its = GameDistributionCoordinates(column = 1, row = 3)
            ),
            GameDistribution(
                should = GameDistributionCoordinates(column = 2, row = 3),
                its = GameDistributionCoordinates(column = 1, row = 2)
            ),
            GameDistribution(
                should = GameDistributionCoordinates(column = 3, row = 3),
                its = GameDistributionCoordinates(column = 3, row = 1)
            )
        )
        val result = service.mixGameDistribution(
            gameDistribution = service.fullGameListSorted(),
            columnCount = 4,
            rowCount = 4,
        )
        assertEquals(result, expected)
    }

    @Test
    fun `getRowFromDistribution - test 2x2`() {
        val service = getService(rows = 2, columns = 2)
        val distribution = service.fullGameListSorted()
        val expectedRow1 = listOf(
            GameDistribution(
                should = GameDistributionCoordinates(
                    column = 0,
                    row = 0,
                ),
                its = GameDistributionCoordinates(
                    column = 0,
                    row = 0,
                )
            ),
            GameDistribution(
                should = GameDistributionCoordinates(
                    column = 1,
                    row = 0,
                ),
                its = GameDistributionCoordinates(
                    column = 1,
                    row = 0,
                )
            ),
        )

        val expectedRow2 = listOf(
            GameDistribution(
                should = GameDistributionCoordinates(
                    column = 0,
                    row = 1,
                ),
                its = GameDistributionCoordinates(
                    column = 0,
                    row = 1,
                )
            ),
            GameDistribution(
                should = GameDistributionCoordinates(
                    column = 1,
                    row = 1,
                ),
                its = GameDistributionCoordinates(
                    column = 1,
                    row = 1,
                )
            )
        )

        val row1 = service.getRowFromDistribution(0, distribution = distribution)
        val row2 = service.getRowFromDistribution(1, distribution = distribution)
        assertEquals(expectedRow1, row1)
        assertEquals(expectedRow2, row2)
    }

    @Test
    fun `extractMixedDistributionAndRows - test 2x2 all mixed`() {
        val service = getService(
            rows = 2,
            columns = 2
        )

        val expected = service.extractMixedDistributionAndRows(
            gameDistribution = mixed2x2
        )

        assertEquals(
            expected,
            Pair(listOf(0, 1), mixed2x2)
        )
    }

    @Test
    fun `extractMixedDistributionAndRows - test 2x2 only 1 mixed`() {
        val service = getService(
            rows = 2,
            columns = 2
        )

        val expected = service.extractMixedDistributionAndRows(
            gameDistribution = onlyOneMixed2x2
        )

        assertEquals(
            expected,
            Pair(
                listOf(1),
                listOf(
                    // 2
                    GameDistribution(
                        should = GameDistributionCoordinates(
                            column = 0,
                            row = 1,
                        ),
                        its = GameDistributionCoordinates(
                            column = 1,
                            row = 1,
                        )
                    ),
                    // 3
                    GameDistribution(
                        should = GameDistributionCoordinates(
                            column = 1,
                            row = 1,
                        ),
                        its = GameDistributionCoordinates(
                            column = 0,
                            row = 1,
                        )
                    ),
                )
            )
        )
    }

    @Test
    fun `mixOnlyNotSortedDistributions - text 4x4 with 2 mixed`() {
//        9, 1, 3, 15 // mixed row 0
//        4, 5, 6, 7
//        10, 2, 8, 0 // mixed row 2
//        11, 12, 13, 14

        // setup
        val unsortedRows = listOf(
            7, 4, 2, 1,
            6, 5, 3, 0,
        )
        val toMixDistribution = listOf(
            // row 0
            GameDistribution(
                should = GameDistributionCoordinates(column = 0, row = 0),
                its = GameDistributionCoordinates(column = 3, row = 2)
            ),
            GameDistribution(
                should = GameDistributionCoordinates(column = 1, row = 0),
                its = GameDistributionCoordinates(column = 1, row = 0)
            ),
            GameDistribution(
                should = GameDistributionCoordinates(column = 2, row = 0),
                its = GameDistributionCoordinates(column = 1, row = 2)
            ),
            GameDistribution(
                should = GameDistributionCoordinates(column = 3, row = 0),
                its = GameDistributionCoordinates(column = 2, row = 0)
            ),
            // row 2
            GameDistribution(
                should = GameDistributionCoordinates(column = 0, row = 2),
                its = GameDistributionCoordinates(column = 2, row = 2)
            ),
            GameDistribution(
                should = GameDistributionCoordinates(column = 1, row = 2),
                its = GameDistributionCoordinates(column = 0, row = 0)
            ),
            GameDistribution(
                should = GameDistributionCoordinates(column = 2, row = 2),
                its = GameDistributionCoordinates(column = 0, row = 2)
            ),
            GameDistribution(
                should = GameDistributionCoordinates(column = 3, row = 2),
                its = GameDistributionCoordinates(column = 0, row = 3)
            ),
        )

        val expected = listOf(
            // row 0
            GameDistribution(
                should = GameDistributionCoordinates(column = 0, row = 0),
                its = GameDistributionCoordinates(column = 3, row = 2)
            ),
            GameDistribution(
                should = GameDistributionCoordinates(column = 1, row = 0),
                its = GameDistributionCoordinates(column = 3, row = 0)
            ),
            GameDistribution(
                should = GameDistributionCoordinates(column = 2, row = 0),
                its = GameDistributionCoordinates(column = 2, row = 0)
            ),
            GameDistribution(
                should = GameDistributionCoordinates(column = 3, row = 0),
                its = GameDistributionCoordinates(column = 2, row = 2)
            ),
            // row 1
            GameDistribution(
                should = GameDistributionCoordinates(column = 0, row = 1),
                its = GameDistributionCoordinates(column = 0, row = 1),
            ),
            GameDistribution(
                should = GameDistributionCoordinates(column = 1, row = 1),
                its = GameDistributionCoordinates(column = 1, row = 1),
            ),
            GameDistribution(
                should = GameDistributionCoordinates(column = 2, row = 1),
                its = GameDistributionCoordinates(column = 2, row = 1),
            ),
            GameDistribution(
                should = GameDistributionCoordinates(column = 3, row = 1),
                its = GameDistributionCoordinates(column = 3, row = 1),
            ),
            // row 2
            GameDistribution(
                should = GameDistributionCoordinates(column = 0, row = 2),
                its = GameDistributionCoordinates(column = 1, row = 0)
            ),
            GameDistribution(
                should = GameDistributionCoordinates(column = 1, row = 2),
                its = GameDistributionCoordinates(column = 1, row = 2)
            ),
            GameDistribution(
                should = GameDistributionCoordinates(column = 2, row = 2),
                its = GameDistributionCoordinates(column = 0, row = 2)
            ),
            GameDistribution(
                should = GameDistributionCoordinates(column = 3, row = 2),
                its = GameDistributionCoordinates(column = 0, row = 0)
            ),
            // row 3
            GameDistribution(
                should = GameDistributionCoordinates(column = 0, row = 3),
                its = GameDistributionCoordinates(column = 0, row = 3)
            ),
            GameDistribution(
                should = GameDistributionCoordinates(column = 1, row = 3),
                its = GameDistributionCoordinates(column = 1, row = 3)
            ),
            GameDistribution(
                should = GameDistributionCoordinates(column = 2, row = 3),
                its = GameDistributionCoordinates(column = 2, row = 3)
            ),
            GameDistribution(
                should = GameDistributionCoordinates(column = 3, row = 3),
                its = GameDistributionCoordinates(column = 3, row = 3)
            )
        )
        val service = getService(
            numbUtilsService = gameNumbUtilsMockService(
                unsortedListStub = unsortedRows
            )
        )

        // act
        val result = service.mixOnlyNotSortedDistributions(
            toMixRows = listOf(0, 2),
            toMixDistribution = toMixDistribution
        )

        // assert
        assertEquals(expected, result)
    }
}

















