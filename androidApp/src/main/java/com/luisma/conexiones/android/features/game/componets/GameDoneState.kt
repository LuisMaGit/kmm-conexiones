package com.luisma.conexiones.android.features.game.componets

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationEndReason
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.keyframes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.stringResource
import com.luisma.conexiones.android.R
import com.luisma.conexiones.android.core_ui.components.CText
import com.luisma.conexiones.android.core_ui.components.game_cards.GUESSED_CARD_ANIMATION_DURATION
import com.luisma.conexiones.android.core_ui.theme.cFontSize36sp

@Composable
fun GameDoneState(
    modifier: Modifier = Modifier,
    win: Boolean,
    lost: Boolean,
    delay: Int,
    withAnimation: Boolean,
    dismissAnimation: () -> Unit,
) {

    if (!win && !lost) return

    val scaleAnimation = remember {
        Animatable(0f)
    }
    LaunchedEffect(key1 = withAnimation) {
        if (withAnimation) {
            val result = scaleAnimation.animateTo(
                targetValue = 1f,
                animationSpec = keyframes {
                    delayMillis = delay
                    durationMillis = GUESSED_CARD_ANIMATION_DURATION
                    1.2f at GUESSED_CARD_ANIMATION_DURATION / 2 using FastOutSlowInEasing
                }
            )

            if (result.endReason == AnimationEndReason.Finished) {
                dismissAnimation()
            }
        }
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxWidth()
    ) {
        CText(
            modifier = Modifier
                .graphicsLayer {
                    scaleX = if (withAnimation) scaleAnimation.value else 1f
                    scaleY = if (withAnimation) scaleAnimation.value else 1f
                }
                .align(Alignment.Center),
            text = if (win) {
                stringResource(id = R.string.level_win)
            } else {
                stringResource(id = R.string.level_lost)
            },
            fontSize = cFontSize36sp,
        )
    }
}
