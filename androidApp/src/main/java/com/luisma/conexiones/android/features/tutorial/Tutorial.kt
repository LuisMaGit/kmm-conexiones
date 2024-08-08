package com.luisma.conexiones.android.features.tutorial

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.luisma.conexiones.android.R
import com.luisma.conexiones.android.core_ui.components.CBottomSheet
import com.luisma.conexiones.android.core_ui.components.CText
import com.luisma.conexiones.android.core_ui.theme.CThemeProvider
import com.luisma.conexiones.android.features.tutorial.componets.KHBullets
import com.luisma.conexiones.android.features.tutorial.componets.KHDifficultyGraph
import com.luisma.conexiones.android.features.tutorial.componets.KHErrorTags
import com.luisma.conexiones.android.features.tutorial.componets.KHExample
import com.luisma.conexiones.android.features.tutorial.componets.KHExpBullets

@Composable
fun Tutorial() {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier.scrollable(
            state = scrollState,
            orientation = Orientation.Vertical
        )
    ) {
        CText(
            modifier = Modifier.padding(
                vertical = TutorialContracts.PADDING_BOTTOM_HEADER_1
            ),
            text = stringResource(id = R.string.kh_subtitle),
        )
        KHBullets()
        KHErrorTags()
        CText(
            modifier = Modifier.padding(
                top = TutorialContracts.PADDING_TOP_HEADER_EXP,
                bottom = TutorialContracts.PADDING_BOTTOM_HEADER_EXP
            ),
            text = stringResource(id = R.string.kh_ex_title),
            fontSize = TutorialContracts.FONT_SIZE_HEADER_EXP
        )
        KHExpBullets()
        KHExample()
        CText(
            modifier = Modifier.padding(vertical = TutorialContracts.PADDING_V_DIFF_HEADER),
            text = stringResource(id = R.string.kh_ex_text_2),
            fontWeight = FontWeight.Normal,
        )
        KHDifficultyGraph()
    }
}

object TutorialContracts {
    val PADDING_BOTTOM_HEADER_1 = 16.dp
    val PADDING_TOP_HEADER_EXP = 24.dp
    val PADDING_BOTTOM_HEADER_EXP = 16.dp
    val FONT_SIZE_HEADER_EXP = 20.sp
    val PADDING_V_DIFF_HEADER = 16.dp
}

@Composable
fun TutorialBottomSheet(
    onDismiss: () -> Unit
) {
    CBottomSheet(
        show = true,
        onDismiss = onDismiss,
        title = stringResource(id = R.string.kh_title)
    ) {
        Tutorial()
    }
}

@Preview(
    showBackground = true,
    backgroundColor = 0XFFFFFF
)
@Composable
private fun TutorialPreview() {
    CThemeProvider {
        Tutorial()
    }
}