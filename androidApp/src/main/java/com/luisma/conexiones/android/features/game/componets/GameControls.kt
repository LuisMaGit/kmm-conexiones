package com.luisma.conexiones.android.features.game.componets

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.luisma.conexiones.android.R
import com.luisma.conexiones.android.core_ui.components.CButton
import com.luisma.conexiones.android.core_ui.theme.CColor
import com.luisma.conexiones.android.core_ui.theme.cSpace16
import com.luisma.conexiones.android.core_ui.theme.cSpace4
import com.luisma.conexiones.android.features.game.GameViewEvents

@Composable
fun GameControls(
    modifier: Modifier = Modifier,
    sendEvent: (event: GameViewEvents) -> Unit,
    canDeselectWords: Boolean,
    canSendRow: Boolean
) {
    Column(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.padding(bottom = cSpace16)
        ) {
            CButton(
                modifier = Modifier
                    .padding(end = cSpace4)
                    .weight(0.5f),
                text = stringResource(id = R.string.level_btn_uncheck),
                iconId = R.drawable.ic_broom_solid,
                color = CColor.white,
                disabled = !canDeselectWords
            ) {
                sendEvent(GameViewEvents.ClearSelection)
            }
            CButton(
                modifier = Modifier.weight(0.5f),
                text = stringResource(id = R.string.level_btn_rotate),
                iconId = R.drawable.ic_shuffle,
                color = CColor.white,
            ) {
                sendEvent(GameViewEvents.MixGame)
            }
        }
        CButton(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(id = R.string.level_btn_send),
            iconId = R.drawable.ic_rocket,
            disabled = !canSendRow
        ) {
            sendEvent(GameViewEvents.Submit)
        }
    }
}