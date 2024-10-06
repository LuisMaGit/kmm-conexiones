package com.luisma.conexiones.android.core_ui.components.game_cards

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationEndReason
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.keyframes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.luisma.conexiones.android.core_ui.components.CText
import com.luisma.conexiones.android.core_ui.theme.cBorderRadius4
import com.luisma.conexiones.android.core_ui.theme.cSpace4

enum class GuessedCardAnimationType {
    NoAnimation,
    Scale
}

const val GUESSED_CARD_ANIMATION_DURATION = 500

@Composable
fun GuessedCard(
    modifier: Modifier = Modifier,
    row: Int,
    description: String,
    words: String,
    color: Color,
    sizeType: GameCardsContracts = GameCardsContracts.Mobile,
    animationType: GuessedCardAnimationType = GuessedCardAnimationType.NoAnimation,
    scaleOrder: Int,
    dismissAnimation: (row: Int) -> Unit,
) {

    val triggerScale = animationType == GuessedCardAnimationType.Scale
    val scaleAnimation = remember {
        Animatable(0f)
    }


    val animationDelay = scaleOrder * GUESSED_CARD_ANIMATION_DURATION
    LaunchedEffect(key1 = triggerScale) {
        if (triggerScale) {
            val result = scaleAnimation.animateTo(
                targetValue = 1f,
                animationSpec = keyframes {
                    delayMillis = animationDelay
                    durationMillis = GUESSED_CARD_ANIMATION_DURATION
                    1.5f at 250 using FastOutSlowInEasing
                }
            )

            if (result.endReason == AnimationEndReason.Finished) {
                dismissAnimation(row)
            }
        }
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.size(
            width = sizeType.guessedCardWidth,
            height = sizeType.cardHeight
        ),
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .clip(RoundedCornerShape(cBorderRadius4))
                .background(color = color)
                .graphicsLayer {
                    scaleX = if (triggerScale) scaleAnimation.value else 1f
                    scaleY = if (triggerScale) scaleAnimation.value else 1f
                }
                .align(Alignment.Center)
                .size(
                    width = if (triggerScale) sizeType.guessedCardWidth * scaleAnimation.value else sizeType.guessedCardWidth,
                    height = if (triggerScale) sizeType.cardHeight * scaleAnimation.value else sizeType.cardHeight
                )
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                CText(
                    modifier = Modifier.padding(bottom = cSpace4),
                    text = description,
                    fontSize = sizeType.guessedCardTitleFontSize,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
                CText(
                    text = words,
                    fontSize = sizeType.guessedCardDescFontSize,
                    fontWeight = FontWeight.Normal,
                    textAlign = TextAlign.Center
                )
            }
        }
    }

}
