package com.luisma.conexiones.android.features.lives

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.luisma.conexiones.android.R
import com.luisma.conexiones.android.core_ui.components.CButton
import com.luisma.conexiones.android.core_ui.components.CIcon
import com.luisma.conexiones.android.core_ui.components.CText
import com.luisma.conexiones.android.core_ui.helpers.fontSizeNonScaledSp
import com.luisma.conexiones.android.core_ui.theme.CColor
import com.luisma.conexiones.android.core_ui.theme.CThemeProvider
import com.luisma.conexiones.android.core_ui.theme.cFontSize20sp
import com.luisma.conexiones.android.core_ui.theme.cFontSize32
import com.luisma.conexiones.android.core_ui.theme.cSpace24
import com.luisma.conexiones.android.core_ui.theme.cSpace4

@Composable
fun LivesContent(
    state: LivesState,
    sendEvent: (event: LivesEvents) -> Unit
) {

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
            text = "+1",
            iconId = R.drawable.ic_heart_solid,
            color = CColor.orange,
            onTap = { sendEvent(LivesEvents.GetLives) }
        )
    }


}

@Preview
@Composable
private fun LivesContentPreview() {
    CThemeProvider {
        LivesContent(
            state = LivesState.initial().copy(lives = 1),
            sendEvent = {}
        )
    }
}