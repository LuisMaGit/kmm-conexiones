package com.luisma.conexiones.android.features.tutorial

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.luisma.conexiones.android.R
import com.luisma.conexiones.android.core_ui.components.CBottomSheet
import com.luisma.conexiones.android.core_ui.components.CText
import com.luisma.conexiones.android.core_ui.theme.CThemeProvider
import com.luisma.conexiones.android.core_ui.theme.cFontSize20sp
import com.luisma.conexiones.android.core_ui.theme.cSpace16
import com.luisma.conexiones.android.core_ui.theme.cSpace24
import com.luisma.conexiones.android.features.tutorial.componets.KHBullets
import com.luisma.conexiones.android.features.tutorial.componets.KHDifficultyGraph
import com.luisma.conexiones.android.features.tutorial.componets.KHErrorTags
import com.luisma.conexiones.android.features.tutorial.componets.KHExample
import com.luisma.conexiones.android.features.tutorial.componets.KHExpBullets

@Composable
fun Tutorial() {
    val scrollState = rememberScrollState()
    LazyColumn(
        modifier = Modifier
            .scrollable(
                state = scrollState,
                orientation = Orientation.Vertical
            )
    ) {

        item {
            CText(
                modifier = Modifier.padding(
                    vertical = cSpace16
                ),
                text = stringResource(id = R.string.kh_subtitle),
            )
            KHBullets()
        }
        item {
            KHErrorTags()
        }
        item {
            CText(
                modifier = Modifier.padding(
                    top = cSpace24,
                    bottom = cSpace16
                ),
                text = stringResource(id = R.string.kh_ex_title),
                fontSize = cFontSize20sp
            )
            KHExpBullets()
            KHExample()
        }
        item {
            CText(
                modifier = Modifier.padding(vertical = cSpace16),
                text = stringResource(id = R.string.kh_ex_text_2),
                fontWeight = FontWeight.Normal,
            )
            KHDifficultyGraph()
        }
    }
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