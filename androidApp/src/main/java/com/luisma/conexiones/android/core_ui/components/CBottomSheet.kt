package com.luisma.conexiones.android.core_ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.luisma.conexiones.android.R
import com.luisma.conexiones.android.core_ui.theme.CTheme
import com.luisma.conexiones.android.core_ui.theme.cBorderRadius8
import com.luisma.conexiones.android.core_ui.theme.cFontSize36sp
import com.luisma.conexiones.android.core_ui.theme.cSpace18
import com.luisma.conexiones.android.core_ui.theme.cSpace24
import com.luisma.conexiones.android.core_ui.theme.cSpace40
import com.luisma.conexiones.android.core_ui.theme.cSpace60
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CBottomSheet(
    modifier: Modifier = Modifier,
    show: Boolean,
    onDismiss: () -> Unit,
    title: String = "",
    content: @Composable () -> Unit
) {

    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(key1 = show) {
        if (!show && sheetState.isVisible) {
            sheetState.hide()
        }

        if (show && !sheetState.isVisible) {
            sheetState.expand()
        }
    }

    if (show) {
        ModalBottomSheet(
            modifier = modifier,
            sheetState = sheetState,
            onDismissRequest = { onDismiss() },
            dragHandle = { Box {} },
            containerColor = CTheme.colors.screenBackground,
            shape = RoundedCornerShape(
                topStart = cBorderRadius8,
                topEnd = cBorderRadius8
            )
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        top = cSpace40,
                        bottom = cSpace24,
                        start = cSpace18,
                        end = cSpace18,
                    )
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    CText(
                        modifier = Modifier.weight(.9f),
                        text = title,
                        fontSize = cFontSize36sp
                    )
                    CIconButton(
                        modifier = Modifier.weight(.1f),
                        id = R.drawable.ic_close,
                        iconSize = 16.dp,
                        onTap = {
                            coroutineScope.launch {
                                sheetState.hide()
                                onDismiss()
                            }
                        }
                    )
                }
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = cSpace18)
            ) {
                content()
            }
            Spacer(
                modifier = Modifier.padding(
                    bottom = cSpace60
                )
            )
        }
    }
}
