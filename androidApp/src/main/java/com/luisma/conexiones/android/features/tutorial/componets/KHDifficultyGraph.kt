package com.luisma.conexiones.android.features.tutorial.componets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.luisma.conexiones.android.R
import com.luisma.conexiones.android.core_ui.components.CText
import com.luisma.conexiones.android.core_ui.helpers.fontSizeNonScaledSp
import com.luisma.conexiones.android.core_ui.helpers.getColorByDomainColor
import com.luisma.conexiones.android.core_ui.helpers.getColorCoreByDifficulty
import com.luisma.conexiones.android.core_ui.theme.CTheme
import com.luisma.conexiones.contracts.GAME_AMOUNT_DIFFICULTIES

@Composable
fun KHDifficultyGraph(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .padding(top = KHDifficultyGraphContracts.PADDING_TOP)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Column(
            modifier = Modifier.padding(end = KHDifficultyGraphContracts.PADDING_END_FIRST_COLUMN)
        ) {
            for (box in 0..<GAME_AMOUNT_DIFFICULTIES) {
                val color = getColorByDomainColor(getColorCoreByDifficulty(box))
                Box(
                    modifier = Modifier
                        .padding(bottom = KHDifficultyGraphContracts.PADDING_BOTTOM_COLOR_BOX)
                        .background(
                            color = color,
                            shape = RoundedCornerShape(
                                KHDifficultyGraphContracts.BORDER_R_BOTTOM_COLOR_BOX
                            )
                        )
                        .size(KHDifficultyGraphContracts.SIZE_COLOR_BOX)
                )
            }
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CText(
                text = stringResource(id = R.string.kh_ex_graph_simple),
                fontWeight = FontWeight.Normal,
                fontSize = KHDifficultyGraphContracts.FONT_SIZE_TEXT.fontSizeNonScaledSp
            )
            Icon(
                modifier = modifier.height(KHDifficultyGraphContracts.SIZE_ICON_ARROW),
                painter = painterResource(id = R.drawable.pic_big_arrow_down),
                contentDescription = "",
                tint = CTheme.colors.iconDefault
            )
            CText(
                text = stringResource(id = R.string.kh_ex_graph_hard),
                fontWeight = FontWeight.Normal,
                fontSize = KHDifficultyGraphContracts.FONT_SIZE_TEXT.fontSizeNonScaledSp
            )
        }
    }
}

object KHDifficultyGraphContracts {
    val PADDING_TOP = 8.dp
    val PADDING_END_FIRST_COLUMN = 8.dp
    val PADDING_BOTTOM_COLOR_BOX = 8.dp
    val BORDER_R_BOTTOM_COLOR_BOX = 4.dp
    val SIZE_COLOR_BOX = 20.dp
    val SIZE_ICON_ARROW = 74.dp
    const val FONT_SIZE_TEXT = 16
}