package com.luisma.conexiones.android.features.lives

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.luisma.conexiones.android.R
import com.luisma.conexiones.android.core_ui.components.CButton
import com.luisma.conexiones.android.core_ui.components.CIcon
import com.luisma.conexiones.android.core_ui.components.CText
import com.luisma.conexiones.android.core_ui.helpers.LiveRewardHelper
import com.luisma.conexiones.android.core_ui.helpers.fontSizeNonScaledSp
import com.luisma.conexiones.android.core_ui.theme.CColor
import com.luisma.conexiones.android.core_ui.theme.cFontSize20sp
import com.luisma.conexiones.android.core_ui.theme.cFontSize32
import com.luisma.conexiones.android.core_ui.theme.cSpace24
import com.luisma.conexiones.android.core_ui.theme.cSpace4

@Composable
fun LivesContent(
    state: LivesState,
    sendEvent: (event: LivesEvents) -> Unit,
    onDismiss: () -> Unit
) {
    val context = LocalContext.current
    LaunchedEffect(key1 = Unit) {
        if (!state.enabledLivesButton) {
            LiveRewardHelper.loadRewarded(
                context = context,
                onLoaded = {
                    sendEvent(LivesEvents.EnableLivesButton(enable = true))
                }
            )
        }
    }
    DisposableEffect(key1 = Unit) {
        onDispose {
            LiveRewardHelper.removeRewarded()
        }
    }
    Column {
        // heart + lives
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = cSpace24),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            CIcon(
                modifier = Modifier.padding(end = cSpace4),
                id = if (state.hasLife) R.drawable.ic_heart_solid else R.drawable.ic_heart,
                tint = if (state.hasLife) CColor.green else null,
                size = 48.dp
            )
            CText(
                text = "x${state.lives}",
                fontSize = cFontSize32.fontSizeNonScaledSp
            )
        }
        // description
        CText(
            modifier = Modifier.padding(bottom = 24.dp),
            text = if (state.hasLife) {
                stringResource(id = R.string.lives_lives_subtitle)
            } else {
                stringResource(id = R.string.lives_no_lives_subtitle)
            },
            fontSize = cFontSize20sp
        )
        // button
        CButton(
            text = if (state.enabledLivesButton) {
                "+1"
            } else {
                stringResource(id = R.string.reusable_loading_add)
            },
            iconId = if (state.enabledLivesButton) {
                R.drawable.ic_heart_solid
            } else {
                R.drawable.ic_hourglass
            },
            color = CColor.orange,
            disabled = !state.enabledLivesButton,
            onTap = {
                LiveRewardHelper.showRewardedAdd(
                    context,
                    onDismissed = {
                        sendEvent(LivesEvents.OnAddWatched)
                        onDismiss()
                    }
                )
            }
        )
    }
}