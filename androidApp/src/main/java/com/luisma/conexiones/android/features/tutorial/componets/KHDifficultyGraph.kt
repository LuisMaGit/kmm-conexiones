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
import com.luisma.conexiones.android.core_ui.theme.cBorderRadius4
import com.luisma.conexiones.android.core_ui.theme.cFontSize16
import com.luisma.conexiones.android.core_ui.theme.cSpace8
import com.luisma.conexiones.contracts.GAME_AMOUNT_DIFFICULTIES

@Composable
fun KHDifficultyGraph(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .padding(top = cSpace8)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Column(
            modifier = Modifier.padding(end = cSpace8)
        ) {
            for (box in 0..<GAME_AMOUNT_DIFFICULTIES) {
                val color = getColorByDomainColor(getColorCoreByDifficulty(box))
                Box(
                    modifier = Modifier
                        .padding(bottom = cSpace8)
                        .background(
                            color = color,
                            shape = RoundedCornerShape(
                                cBorderRadius4
                            )
                        )
                        .size(20.dp)
                )
            }
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CText(
                text = stringResource(id = R.string.kh_ex_graph_simple),
                fontWeight = FontWeight.Normal,
                fontSize = cFontSize16.fontSizeNonScaledSp
            )
            Icon(
                modifier = modifier.height(74.dp),
                painter = painterResource(id = R.drawable.pic_big_arrow_down),
                contentDescription = "",
                tint = CTheme.colors.iconDefault
            )
            CText(
                text = stringResource(id = R.string.kh_ex_graph_hard),
                fontWeight = FontWeight.Normal,
                fontSize = cFontSize16.fontSizeNonScaledSp
            )
        }
    }
}
