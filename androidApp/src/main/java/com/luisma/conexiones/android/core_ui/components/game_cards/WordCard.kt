package com.luisma.conexiones.android.core_ui.components.game_cards

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationEndReason
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.keyframes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.luisma.conexiones.android.core_ui.components.CText
import com.luisma.conexiones.android.core_ui.theme.CTheme
import com.luisma.conexiones.android.core_ui.theme.cBorderRadius4

@Composable
fun WordCard(
    modifier: Modifier = Modifier,
    disabled: Boolean = false,
    sizeType: GameCardsContracts = GameCardsContracts.Mobile,
    text: String,
    selected: Boolean,
    onTap: (column: Int, row: Int) -> Unit,
    column: Int,
    row: Int,
    animationType: WordCardAnimationType = WordCardAnimationType.NoAnimation,
    dismissAnimation: (column: Int, row: Int) -> Unit
) {

    // animation scale
    val triggerScale = animationType == WordCardAnimationType.Scale
    val scaleAnimation = remember {
        Animatable(1f)
    }
    LaunchedEffect(key1 = triggerScale) {
        if (triggerScale) {
            val result = scaleAnimation.animateTo(
                targetValue = 1f,
                animationSpec = keyframes {
                    durationMillis = 100
                    1.05f at 50 with FastOutSlowInEasing
                }
            )

            if (result.endReason == AnimationEndReason.Finished) {
                dismissAnimation(column, row)
            }
        }
    }

    // animation horizontal move
    val translationXAnimation = remember {
        Animatable(0f)
    }
    val triggerXMove = animationType == WordCardAnimationType.TranslateX
    LaunchedEffect(key1 = triggerXMove) {
        if (triggerXMove) {
            val result = translationXAnimation.animateTo(
                targetValue = 0f,
                animationSpec = keyframes {
                    durationMillis = 250
                    -20f at 50
                    20f at 100
                    -20f at 150
                    20f at 200
                },
            )
            if (result.endReason == AnimationEndReason.Finished) {
                dismissAnimation(column, row)
            }
        }
    }

    // theme
    val color = CTheme.colors
    val baseModifier = Modifier
        .graphicsLayer {
            scaleX = if (triggerScale) scaleAnimation.value else 1f
            scaleY = if (triggerScale) scaleAnimation.value else 1f
            translationX = translationXAnimation.value
        }
        .size(
            width = if (triggerScale) sizeType.wordCardWidth * scaleAnimation.value else sizeType.wordCardWidth,
            height = if (triggerScale) sizeType.cardHeight * scaleAnimation.value else sizeType.cardHeight
        )
        .clip(RoundedCornerShape(cBorderRadius4))
        .background(
            if (selected) {
                color.textDefault
            } else {
                color.softContrastScreenBackground
            }
        )
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .then(
                if (disabled) {
                    baseModifier
                } else {
                    baseModifier.clickable { onTap(column, row) }
                }
            )
    ) {
        CText(
            text = text,
            fontSize = sizeType.wordCardFontSize,
            color = if (selected) {
                color.softContrastScreenBackground
            } else {
                color.textDefault
            },
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            maxLines = 2,
        )
    }
}

enum class WordCardAnimationType {
    NoAnimation,
    Scale,
    TranslateX
}