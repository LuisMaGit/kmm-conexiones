package com.luisma.conexiones.android.core_ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.luisma.conexiones.android.R
import com.luisma.conexiones.android.core_ui.theme.cBorderRadius4


@Composable
fun CLogo(
    modifier: Modifier = Modifier,
    size: Dp = 40.dp
) {
    Image(
        modifier = modifier
            .size(size)
            .clip(RoundedCornerShape(cBorderRadius4)),
        painter = painterResource(id = R.drawable.ic_logo),
        contentDescription = "",
    )
}
