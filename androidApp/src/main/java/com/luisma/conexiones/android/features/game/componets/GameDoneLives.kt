package com.luisma.conexiones.android.features.game.componets

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationEndReason
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.keyframes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import com.luisma.conexiones.android.R
import com.luisma.conexiones.android.core_ui.components.CIcon
import com.luisma.conexiones.android.core_ui.components.game_cards.GUESSED_CARD_ANIMATION_DURATION
import com.luisma.conexiones.android.core_ui.theme.CColor
import com.luisma.conexiones.android.core_ui.theme.CTheme
import com.luisma.conexiones.android.core_ui.theme.cSpace16
import com.luisma.conexiones.contracts.GAME_MAX_LIVES

@Composable
fun GameDoneLives(
    modifier: Modifier = Modifier,
    lives: Int,
    withAnimation: Boolean,
    dismissAnimation: () -> Unit,
) {
    val show = lives > 0
    Box(
        modifier = modifier
    ) {
        if (show)
            Row(
                horizontalArrangement = Arrangement.Center
            ) {
                List(GAME_MAX_LIVES) {
                    DoneLive(
                        filledLive = false
                    )
                }
            }
        Row(
            horizontalArrangement = Arrangement.Center
        ) {
            List(lives) { live ->
                DoneLive(
                    filledLive = true,
                    withAnimation = withAnimation,
                    live = live,
                    lives = lives,
                    disposeLivesAnimation = dismissAnimation,
                )
            }
        }
    }
}

@Composable
private fun DoneLive(
    filledLive: Boolean,
    withAnimation: Boolean = false,
    live: Int = 0,
    lives: Int = 0,
    disposeLivesAnimation: (() -> Unit)? = null
) {
    val size = 34.dp
    val targetScale = 1.1F

    val scaleAnimation = remember {
        Animatable(0f)
    }
    LaunchedEffect(key1 = withAnimation) {
        if (withAnimation) {
            val result = scaleAnimation.animateTo(
                targetValue = targetScale,
                animationSpec = keyframes {
                    delayMillis = live * GUESSED_CARD_ANIMATION_DURATION
                    durationMillis = GUESSED_CARD_ANIMATION_DURATION
                    1.2f at GUESSED_CARD_ANIMATION_DURATION / 2 using FastOutSlowInEasing
                }
            )

            if (result.endReason == AnimationEndReason.Finished &&
                live == lives - 1 &&
                disposeLivesAnimation != null
            ) {
                disposeLivesAnimation()
            }
        }
    }
    if (filledLive) {
        Box(
            contentAlignment = Alignment.Center,
        ) {
            CIcon(
                modifier = Modifier
                    .graphicsLayer {
                        scaleX = if (withAnimation) scaleAnimation.value else targetScale
                        scaleY = if (withAnimation) scaleAnimation.value else targetScale
                    }
                    .align(Alignment.Center)
                    .padding(horizontal = cSpace16),
                id = R.drawable.ic_heart_solid,
                tint = CColor.green,
                size = size
            )
        }
    } else {
        CIcon(
            modifier = Modifier
                .padding(horizontal = cSpace16),
            id = R.drawable.ic_heart,
            tint = CTheme.colors.textDefault,
            size = size
        )
    }

}