package com.luisma.conexiones.android.core_ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.luisma.conexiones.android.core_ui.theme.CColor
import com.luisma.conexiones.android.core_ui.theme.CThemeProvider

@Composable
fun CTryChip(
    modifier: Modifier = Modifier
) {


    Box(
        modifier = modifier
            .size(17.dp)
            .clip(CircleShape)
            .background(CColor.black)
    ) {
        Box(
            modifier = Modifier
                .height(16.dp)
                .fillMaxSize()
                .clip(CircleShape)
                .border(
                    width = 0.75.dp,
                    color = CColor.black,
                    shape = CircleShape
                )
                .padding(0.75.dp)
                .background(
                    color = CColor.white,
                    shape = CircleShape
                )
        )
    }


}

@Preview
@Composable
private fun CTryChipPreview() {
    CThemeProvider {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(70.dp)
                .background(color = CColor.white2)
        ) {
            CTryChip()
        }
    }
}