package com.luisma.conexiones.services

import com.luisma.conexiones.contracts.GAME_WORD_AMOUNT
import com.luisma.conexiones.models.game.GameDistribution
import com.luisma.conexiones.models.game.GameDistributionCoordinates
import com.luisma.conexiones.services.game.GameSelectionService
import com.luisma.conexiones.services.game.GameSelectionSubmitResponse
import kotlin.test.Test
import kotlin.test.assertEquals

class GameSelectionServiceTest {

    private fun getService(
        columns: Int = GAME_WORD_AMOUNT,
    ): GameSelectionService {
        return GameSelectionService(
            columns = columns
        )
    }

    // matrix4x4 mixed
    // whooping     felicity    giant       construction
    // fiction      humor       poetry      travel          -> guessed
    // frasier      big         glee        paper
    // great        cheers      euphoria    huge

    // matrix sorted
    // big          giant       great       huge
    // fiction      humor       poetry      travel
    // cheers       euphoria    felicity    glee
    // construction frasier     paper       whooping

    private val matrix4x4Mixed = listOf(
        GameDistribution(
            should = GameDistributionCoordinates(column = 0, row = 0),
            its = GameDistributionCoordinates(column = 1, row = 2),
        ),
        GameDistribution(
            should = GameDistributionCoordinates(column = 1, row = 0),
            its = GameDistributionCoordinates(column = 2, row = 0),
        ),
        GameDistribution(
            should = GameDistributionCoordinates(column = 2, row = 0),
            its = GameDistributionCoordinates(column = 0, row = 3),
        ),
        GameDistribution(
            should = GameDistributionCoordinates(column = 3, row = 0),
            its = GameDistributionCoordinates(column = 3, row = 3),
        ),
        //
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
        //
        GameDistribution(
            should = GameDistributionCoordinates(column = 0, row = 2),
            its = GameDistributionCoordinates(column = 1, row = 3),
        ),
        GameDistribution(
            should = GameDistributionCoordinates(column = 1, row = 2),
            its = GameDistributionCoordinates(column = 2, row = 3),
        ),
        GameDistribution(
            should = GameDistributionCoordinates(column = 2, row = 2),
            its = GameDistributionCoordinates(column = 1, row = 0),
        ),
        GameDistribution(
            should = GameDistributionCoordinates(column = 3, row = 2),
            its = GameDistributionCoordinates(column = 2, row = 2),
        ),
        //
        GameDistribution(
            should = GameDistributionCoordinates(column = 0, row = 3),
            its = GameDistributionCoordinates(column = 3, row = 0),
        ),
        GameDistribution(
            should = GameDistributionCoordinates(column = 1, row = 3),
            its = GameDistributionCoordinates(column = 0, row = 2),
        ),
        GameDistribution(
            should = GameDistributionCoordinates(column = 2, row = 3),
            its = GameDistributionCoordinates(column = 3, row = 2),
        ),
        GameDistribution(
            should = GameDistributionCoordinates(column = 3, row = 3),
            its = GameDistributionCoordinates(column = 0, row = 0),
        ),
    )

    @Test
    fun `checkRowGuessed - test 4x4 `() {
        val service = getService()
        val row0 = listOf(
            GameDistributionCoordinates(column = 1, row = 2),
            GameDistributionCoordinates(column = 2, row = 0),
            GameDistributionCoordinates(column = 0, row = 3),
            GameDistributionCoordinates(column = 3, row = 3),
        )
        val row1 = listOf(
            GameDistributionCoordinates(column = 0, row = 1),
            GameDistributionCoordinates(column = 1, row = 1),
            GameDistributionCoordinates(column = 2, row = 1),
            GameDistributionCoordinates(column = 3, row = 1),
        )
        val row2 = listOf(
            GameDistributionCoordinates(column = 1, row = 3),
            GameDistributionCoordinates(column = 2, row = 3),
            GameDistributionCoordinates(column = 1, row = 0),
            GameDistributionCoordinates(column = 2, row = 2),
        )
        val row3 = listOf(
            GameDistributionCoordinates(column = 3, row = 0),
            GameDistributionCoordinates(column = 0, row = 2),
            GameDistributionCoordinates(column = 3, row = 2),
            GameDistributionCoordinates(column = 0, row = 0),
        )
        val rowFail = listOf(
            GameDistributionCoordinates(column = 3, row = 0),
            GameDistributionCoordinates(column = 0, row = 2),
            GameDistributionCoordinates(column = 3, row = 1),
            GameDistributionCoordinates(column = 0, row = 0),
        )
        val result0 = service.checkRowGuessed(
            currentDistribution = matrix4x4Mixed,
            currentSelection = row0
        )
        val result1 = service.checkRowGuessed(
            currentDistribution = matrix4x4Mixed,
            currentSelection = row1
        )
        val result2 = service.checkRowGuessed(
            currentDistribution = matrix4x4Mixed,
            currentSelection = row2
        )
        val result3 = service.checkRowGuessed(
            currentDistribution = matrix4x4Mixed,
            currentSelection = row3
        )
        val resultFail = service.checkRowGuessed(
            currentDistribution = matrix4x4Mixed,
            currentSelection = rowFail
        )
        assertEquals(result0, 0)
        assertEquals(result1, 1)
        assertEquals(result2, 2)
        assertEquals(result3, 3)
        assertEquals(resultFail, -1)
    }

    @Test
    fun `insertRowGuessedInDistribution - test 4x4  `() {
        val service = getService()
        val result = service.insertRowGuessedInDistribution(
            currentDistribution = matrix4x4Mixed,
            rowGuessed = 0
        )
        val matrixWithRow1Sorted = listOf(
            GameDistribution(
                should = GameDistributionCoordinates(column = 0, row = 0),
                its = GameDistributionCoordinates(column = 0, row = 0),
            ),
            GameDistribution(
                should = GameDistributionCoordinates(column = 1, row = 0),
                its = GameDistributionCoordinates(column = 1, row = 0),
            ),
            GameDistribution(
                should = GameDistributionCoordinates(column = 2, row = 0),
                its = GameDistributionCoordinates(column = 2, row = 0),
            ),
            GameDistribution(
                should = GameDistributionCoordinates(column = 3, row = 0),
                its = GameDistributionCoordinates(column = 3, row = 0),
            ),
            //
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
            //
            GameDistribution(
                should = GameDistributionCoordinates(column = 0, row = 2),
                its = GameDistributionCoordinates(column = 1, row = 3),
            ),
            GameDistribution(
                should = GameDistributionCoordinates(column = 1, row = 2),
                its = GameDistributionCoordinates(column = 2, row = 3),
            ),
            GameDistribution(
                should = GameDistributionCoordinates(column = 2, row = 2),
                its = GameDistributionCoordinates(column = 0, row = 3),
            ),
            GameDistribution(
                should = GameDistributionCoordinates(column = 3, row = 2),
                its = GameDistributionCoordinates(column = 2, row = 2),
            ),
            //
            GameDistribution(
                should = GameDistributionCoordinates(column = 0, row = 3),
                its = GameDistributionCoordinates(column = 3, row = 3),
            ),
            GameDistribution(
                should = GameDistributionCoordinates(column = 1, row = 3),
                its = GameDistributionCoordinates(column = 0, row = 2),
            ),
            GameDistribution(
                should = GameDistributionCoordinates(column = 2, row = 3),
                its = GameDistributionCoordinates(column = 3, row = 2),
            ),
            GameDistribution(
                should = GameDistributionCoordinates(column = 3, row = 3),
                its = GameDistributionCoordinates(column = 1, row = 2),
            ),
        )

        assertEquals(
            result,
            GameSelectionSubmitResponse(
                newDistribution = matrixWithRow1Sorted,
                rowGuessed = 0
            )
        )
    }

}