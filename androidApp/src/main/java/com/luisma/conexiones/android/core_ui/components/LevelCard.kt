package com.luisma.conexiones.android.core_ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.luisma.conexiones.android.R
import com.luisma.conexiones.android.core_ui.helpers.fontSizeNonScaledSp
import com.luisma.conexiones.android.core_ui.theme.CColor
import com.luisma.conexiones.android.core_ui.theme.CTheme
import com.luisma.conexiones.android.core_ui.theme.CThemeProvider

@Composable
fun LevelCard(
    modifier: Modifier = Modifier,
    card: LevelCardData,
    loading: Boolean = false,
    onAppear: () -> Unit = {},
    onDestroy: () -> Unit = {},
    checkVisibility: Boolean = false
) {

    LaunchedEffect(key1 = Unit) {
        if (checkVisibility) {
            onAppear()
        }
    }

    DisposableEffect(key1 = Unit) {
        onDispose {
            if (checkVisibility) {
                onDestroy()
            }
        }
    }

    val colors = CTheme.colors

    fun smallCardColor(): Color {
        return when (card.type) {
            LevelCardType.Lost -> CColor.red
            LevelCardType.Win -> CColor.green
            LevelCardType.Playing -> CColor.yellow
            LevelCardType.Unlock, LevelCardType.Lock -> CColor.black
        }
    }

    fun smallCardText(): Int {
        return when (card.type) {
            LevelCardType.Lost -> R.string.levels_card_lost
            LevelCardType.Win -> R.string.levels_card_win
            LevelCardType.Playing -> R.string.levels_card_playing
            LevelCardType.Unlock, LevelCardType.Lock -> 0
        }
    }

    @Composable
    fun BottomComponent() {
        return when (card.type) {
            LevelCardType.Lost -> LevelCardHeart(
                lives = card.livesReward,
                color = colors.textDefault,
                colorText = colors.softContrastScreenBackground
            )

            LevelCardType.Win -> LevelCardHeart(
                lives = card.livesReward,
                color = CColor.green,
                colorText = colors.softContrastScreenBackground
            )

            LevelCardType.Playing, LevelCardType.Unlock -> CIcon(
                id = R.drawable.ic_unlock_solid,
            )

            LevelCardType.Lock -> CIcon(
                id = R.drawable.ic_lock,
            )
        }
    }

    Box(
        modifier = modifier
            .size(LevelCardContracts.SIZE)
            .background(
                color = colors.softContrastScreenBackground,
                shape = RoundedCornerShape(LevelCardContracts.BORDER_R)
            ),
        contentAlignment = Alignment.Center
    ) {
        if (!loading) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // level state card
                if (card.type != LevelCardType.Unlock && card.type != LevelCardType.Lock) {
                    Box(
                        modifier = Modifier
                            .background(
                                color = smallCardColor(),
                                shape = RoundedCornerShape(LevelCardContracts.SMALL_CARD_BORDER_R)
                            )
                            .size(
                                width = LevelCardContracts.SMALL_CARD_WIDTH,
                                height = LevelCardContracts.SMALL_CARD_HEIGHT
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        CText(
                            text = stringResource(id = smallCardText()),
                            fontSize = LevelCardContracts.SMALL_CARD_FONT.fontSizeNonScaledSp,
                            color = colors.softContrastScreenBackground
                        )
                    }
                } else {
                    Box(
                        modifier = Modifier.size(LevelCardContracts.SMALL_CARD_HEIGHT)
                    )
                }
                // level number
                CText(
                    modifier = Modifier.padding(
                        vertical = LevelCardContracts.LEVELS_FONT_PADDING
                    ),
                    text = card.level,
                    fontSize = LevelCardContracts.LEVELS_FONT_SIZE.fontSizeNonScaledSp
                )
                // lives or lock
                BottomComponent()
            }
        }
    }
}


@Composable
fun LevelCardHeart(
    lives: String,
    color: Color,
    colorText: Color
) {
    Box(
        contentAlignment = Alignment.Center,
    ) {
        CIcon(
            id = R.drawable.ic_heart_solid,
            tint = color,
        )
        CText(
            modifier = Modifier.padding(bottom = LevelCardContracts.HEART_FONT_PADDING),
            text = lives,
            color = colorText,
            fontSize = LevelCardContracts.HEART_FONT_SIZE.fontSizeNonScaledSp
        )
    }
}

object LevelCardContracts {
    val BORDER_R = 8.dp
    val SIZE = 140.dp
    const val LEVELS_FONT_SIZE = 64
    val LEVELS_FONT_PADDING = 2.dp
    const val HEART_FONT_SIZE = 12
    val HEART_FONT_PADDING = 2.dp
    val SMALL_CARD_BORDER_R = 4.dp
    val SMALL_CARD_WIDTH = 68.dp
    val SMALL_CARD_HEIGHT = 20.dp
    const val SMALL_CARD_FONT = 12
}

data class LevelCardData(
    val type: LevelCardType,
    val level: String,
    val livesReward: String = "",
) {
    companion object {
        fun empty(): LevelCardData {
            return LevelCardData(
                type = LevelCardType.Lock,
                level = "",
            )
        }
    }
}

enum class LevelCardType {
    Lost,
    Win,
    Lock,
    Playing,
    Unlock
}


@Preview
@Composable
private fun LevelCardPreview() {
    CThemeProvider {
        Column {
            LevelCard(
                card = LevelCardData(
                    type = LevelCardType.Win,
                    level = "1",
                    livesReward = "+2"
                )
            )
            LevelCard(
                card = LevelCardData(
                    type = LevelCardType.Lost,
                    level = "2",
                    livesReward = "-1"
                )
            )
            LevelCard(
                card = LevelCardData(
                    type = LevelCardType.Lock,
                    level = "3",
                )
            )
            LevelCard(
                card = LevelCardData(
                    type = LevelCardType.Playing,
                    level = "4",
                )
            )
        }
    }
}

@Preview
@Composable
private fun LevelCardDarkPreview() {
    CThemeProvider(
        darkTheme = true
    ) {
        Column {
            LevelCard(
                card = LevelCardData(
                    type = LevelCardType.Win,
                    level = "1",
                    livesReward = "+2"
                )
            )
            LevelCard(
                card = LevelCardData(
                    type = LevelCardType.Lost,
                    level = "2",
                    livesReward = "-1"
                )
            )
            LevelCard(
                card = LevelCardData(
                    type = LevelCardType.Lock,
                    level = "3",
                )
            )
            LevelCard(
                card = LevelCardData(
                    type = LevelCardType.Playing,
                    level = "4",
                )
            )
        }
    }
}