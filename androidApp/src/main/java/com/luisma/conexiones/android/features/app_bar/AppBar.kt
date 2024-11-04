package com.luisma.conexiones.android.features.app_bar

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.luisma.conexiones.android.R
import com.luisma.conexiones.android.core_ui.components.CIconButton
import com.luisma.conexiones.android.core_ui.components.CLogo
import com.luisma.conexiones.android.core_ui.components.CText
import com.luisma.conexiones.android.core_ui.theme.CColor
import com.luisma.conexiones.android.core_ui.theme.cDefaultIconSize
import com.luisma.conexiones.android.core_ui.theme.cSpace16
import com.luisma.conexiones.android.core_ui.theme.cSpace24

@Composable
fun AppBar(
    modifier: Modifier = Modifier,
    leadingComponentType: AppBarLeadingComponentType = AppBarLeadingComponentType.BackArrow,
    onTapLogo: (() -> Unit)? = null,
    onBack: (() -> Unit)? = null,
    sendEvent: (event: AppBarEvents) -> Unit,
    state: AppBarState,
    lives: Int? = null,
    openTutorial: Boolean = false
) {

    val iconSize = 24.dp

    LaunchedEffect(key1 = lives) {
        if (openTutorial) {
            sendEvent(AppBarEvents.ToggleTutorial)
        }
        sendEvent(AppBarEvents.SetLives(lives))
    }

    Row(
        modifier = modifier
            .padding(horizontal = cSpace16)
            .height(50.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        when (leadingComponentType) {
            AppBarLeadingComponentType.BackArrow ->
                CIconButton(
                    id = R.drawable.ic_arrow_left_solid,
                    onTap = {
                        if (onBack != null) {
                            onBack()
                        }
                    },
                    iconSize = iconSize
                )

            AppBarLeadingComponentType.Logo -> CLogo(
                modifier = Modifier.clickable {
                    if (onTapLogo != null) {
                        onTapLogo()
                    }
                },
                size = iconSize
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        CIconButton(
            modifier = Modifier.padding(end = cSpace24),
            id = R.drawable.ic_question,
            onTap = { sendEvent(AppBarEvents.ToggleTutorial) },
        )
        CIconButton(
            modifier = Modifier.padding(end = cSpace24),
            id = R.drawable.ic_chart_bar,
            onTap = { sendEvent(AppBarEvents.ToggleStats) }
        )
        AppbarIconLives(
            hasLife = state.hasLife,
            onTap = { sendEvent(AppBarEvents.ToggleLives) }
        )
        Box(
            contentAlignment = Alignment.BottomCenter,
            modifier = Modifier.height(cDefaultIconSize)
        ) {
            CText(
                text = "x${state.lives}"
            )
        }

    }

}

enum class AppBarLeadingComponentType {
    Logo,
    BackArrow
}

@Composable
fun AppbarIconLives(
    onTap: () -> Unit,
    hasLife: Boolean,
) {

    if (hasLife) {
        CIconButton(
            id = R.drawable.ic_heart_solid,
            iconColor = CColor.green,
            onTap = onTap
        )
    } else {
        val infiniteTransition = rememberInfiniteTransition(label = "cLives")
        val animation by infiniteTransition.animateFloat(
            targetValue = 1.5f,
            initialValue = 1f,
            label = "c_Lives",
            animationSpec = infiniteRepeatable(
                animation = tween(800, easing = FastOutSlowInEasing),
                repeatMode = RepeatMode.Restart
            )
        )
        CIconButton(
            modifier = Modifier.graphicsLayer {
                scaleY = animation
                scaleX = animation
            },
            id = R.drawable.ic_heart,
            onTap = onTap
        )
    }
}

@Preview
@Composable
private fun AppBarPreview() {
    AppBar(
        sendEvent = {},
        lives = 1,
        state = AppBarState.initial()
    )
}