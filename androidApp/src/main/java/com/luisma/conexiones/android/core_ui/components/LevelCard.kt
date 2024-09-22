package com.luisma.conexiones.android.core_ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.luisma.conexiones.android.R
import com.luisma.conexiones.android.core_ui.helpers.fontSizeNonScaledSp
import com.luisma.conexiones.android.core_ui.theme.CColor
import com.luisma.conexiones.android.core_ui.theme.CTheme
import com.luisma.conexiones.android.core_ui.theme.CThemeProvider
import com.luisma.conexiones.android.core_ui.theme.cBorderRadius4
import com.luisma.conexiones.android.core_ui.theme.cBorderRadius8
import com.luisma.conexiones.android.core_ui.theme.cFontSize12
import com.luisma.conexiones.android.core_ui.theme.cFontSize64sp

@Composable
fun LevelCard(
    modifier: Modifier = Modifier,
    card: LevelCardData,
    loading: Boolean = false,
    onAppear: () -> Unit = {},
    onDestroy: () -> Unit = {},
    onTap: (id: Int) -> Unit,
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
    val smallCardHeight = 20.dp

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

    val baseModifier = modifier
        .size(levelsCardSize)
        .clip(RoundedCornerShape(cBorderRadius8))
        .background(
            color = colors.softContrastScreenBackground
        )

    val optionalModifier = if (card.type == LevelCardType.Lock || loading) {
        baseModifier
    } else baseModifier.clickable {
        onTap(card.id)
    }

    Box(
        modifier = optionalModifier,
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
                                shape = RoundedCornerShape(cBorderRadius4)
                            )
                            .size(
                                width = 68.dp,
                                height = smallCardHeight
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        CText(
                            text = stringResource(id = smallCardText()),
                            fontSize = cFontSize12.fontSizeNonScaledSp,
                            color = colors.softContrastScreenBackground
                        )
                    }
                } else {
                    Box(
                        modifier = Modifier.size(smallCardHeight)
                    )
                }
                // level number
                CText(
                    modifier = Modifier.padding(
                        vertical = 2.dp
                    ),
                    text = card.level,
                    fontSize = cFontSize64sp
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
            modifier = Modifier.padding(bottom = 2.dp),
            text = lives,
            color = colorText,
            fontSize = cFontSize12.fontSizeNonScaledSp
        )
    }
}

val levelsCardSize = 140.dp


data class LevelCardData(
    val id: Int,
    val type: LevelCardType,
    val level: String,
    val livesReward: String = "",
) {
    companion object {
        fun empty(): LevelCardData {
            return LevelCardData(
                id = -1,
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
                    id = -1,
                    type = LevelCardType.Win,
                    level = "1",
                    livesReward = "+2"
                ),
                onTap = {}
            )
            LevelCard(
                card = LevelCardData(
                    id = -1,
                    type = LevelCardType.Lost,
                    level = "2",
                    livesReward = "-1"
                ),
                onTap = {}
            )
            LevelCard(
                card = LevelCardData(
                    id = -1,
                    type = LevelCardType.Lock,
                    level = "3",
                ),
                onTap = {}
            )
            LevelCard(
                card = LevelCardData(
                    id = -1,
                    type = LevelCardType.Playing,
                    level = "4",
                ),
                onTap = {}
            )
        }
    }
}