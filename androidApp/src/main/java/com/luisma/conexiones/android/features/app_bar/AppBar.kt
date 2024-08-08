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
import com.luisma.conexiones.android.core_ui.components.C_ICON_SIZE
import com.luisma.conexiones.android.core_ui.theme.CColor

@Composable
fun AppBar(
    modifier: Modifier = Modifier,
    leadingComponentType: AppBarLeadingComponentType = AppBarLeadingComponentType.BackArrow,
    onTapLogo: (() -> Unit)? = null,
    sendEvent: (event: AppBarEvents) -> Unit,
    state: AppBarState,
    lives: Int?,
    openTutorial: Boolean = false
) {

    LaunchedEffect(key1 = lives) {
        if (openTutorial) {
            sendEvent(AppBarEvents.ToggleTutorial)
        }
        sendEvent(AppBarEvents.SetLives(lives))
    }

    Row(
        modifier = modifier
            .padding(
                horizontal = AppBarContracts.HORIZONTAL_PADDING
            )
            .height(
                AppBarContracts.HEIGHT
            ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        when (leadingComponentType) {
            AppBarLeadingComponentType.BackArrow ->
                CIconButton(
                    id = R.drawable.ic_arrow_left_solid,
                    onTap = {}
                )

            AppBarLeadingComponentType.Logo -> CLogo(
                modifier = Modifier.clickable {
                    if (onTapLogo != null) {
                        onTapLogo()
                    }
                },
                size = AppBarContracts.LOGO_SIZE
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        CIconButton(
            modifier = Modifier.padding(end = AppBarContracts.ICONS_PADDING),
            id = R.drawable.ic_question,
            onTap = { sendEvent(AppBarEvents.ToggleTutorial) },
        )
        CIconButton(
            modifier = Modifier.padding(end = AppBarContracts.ICONS_PADDING),
            id = R.drawable.ic_chart_bar,
            onTap = { sendEvent(AppBarEvents.ToggleStats) }
        )
        AppbarIconLives(
            hasLife = state.hasLife,
            onTap = { sendEvent(AppBarEvents.ToggleLives) }
        )
        Box(
            contentAlignment = Alignment.BottomCenter,
            modifier = Modifier.height(C_ICON_SIZE)
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

object AppBarContracts {
    val HEIGHT = 50.dp
    val ICONS_PADDING = 24.dp
    val HORIZONTAL_PADDING = 16.dp
    val LOGO_SIZE = 30.dp
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