package com.luisma.conexiones.android.features.game.componets

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.luisma.conexiones.android.R
import com.luisma.conexiones.android.core_ui.components.CButton
import com.luisma.conexiones.android.core_ui.components.CText
import com.luisma.conexiones.android.core_ui.helpers.LiveRewardHelper
import com.luisma.conexiones.android.core_ui.theme.CColor
import com.luisma.conexiones.android.core_ui.theme.cFontSize14sp
import com.luisma.conexiones.android.core_ui.theme.cSpace16


@Composable
fun GameDoneMoreLives(
    modifier: Modifier = Modifier,
    enableLivesButton: (enabled: Boolean) -> Unit,
    enabledLivesButton: Boolean,
    onAdWatched: ()-> Unit
    ) {
    val context = LocalContext.current
    LaunchedEffect(key1 = Unit) {
        if (!enabledLivesButton) {
            LiveRewardHelper.loadRewarded(
                context = context,
                onLoaded = {
                    enableLivesButton(true)
                }
            )
        }
    }
    DisposableEffect(key1 = Unit) {
        onDispose {
            LiveRewardHelper.removeRewarded()
        }
    }

    Column(
        modifier = modifier,
    ) {
        CText(
            modifier = Modifier.padding(bottom = cSpace16),
            text = stringResource(id = R.string.level_lost_no_lives),
            fontSize = cFontSize14sp
        )
        CButton(
            text = if (enabledLivesButton) {
                "+1"
            } else {
                stringResource(id = R.string.reusable_loading_add)
            },
            iconId = if (enabledLivesButton) {
                R.drawable.ic_heart_solid
            } else {
                R.drawable.ic_hourglass
            },
            color = CColor.orange,
            disabled = !enabledLivesButton,
            onTap = {
                LiveRewardHelper.showRewardedAdd(
                    context,
                    onDismissed = {
                        onAdWatched()
                    }
                )
            }
        )
    }
}