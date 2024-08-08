package com.luisma.conexiones.android.core_ui.components

import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.unit.sp
import com.luisma.conexiones.android.R
import com.luisma.conexiones.android.core_ui.theme.CTheme
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
                topStart = CBottomSheetContracts.BORDER_R,
                topEnd = CBottomSheetContracts.BORDER_R
            )
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        top = CBottomSheetContracts.PADDING_HEADER_TOP,
                        bottom = CBottomSheetContracts.PADDING_HEADER_BOTTOM,
                        start = CBottomSheetContracts.PADDING_H_CONTENT,
                        end = CBottomSheetContracts.PADDING_H_CONTENT,
                    )
            ) {
                Row (
                    verticalAlignment = Alignment.CenterVertically
                ){
                    CText(
                        modifier = Modifier.weight(.9f),
                        text = title,
                        fontSize = CBottomSheetContracts.TITLE_FONT_SIZE
                    )
                    CIconButton(
                        modifier = Modifier.weight(.1f),
                        id = R.drawable.ic_close,
                        iconSize = CBottomSheetContracts.CLOSE_ICON_SIZE,
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
                    .padding(horizontal = CBottomSheetContracts.PADDING_H_CONTENT)
            ) {
                content()
            }
            Spacer(modifier = Modifier.padding(bottom = CBottomSheetContracts.PADDING_BOTTOM))
        }
    }
}

object CBottomSheetContracts {
    val BORDER_R = 8.dp
    val CLOSE_ICON_SIZE = 16.dp
    val PADDING_HEADER_TOP = 40.dp
    val PADDING_HEADER_BOTTOM = 24.dp
    val PADDING_H_CONTENT = 18.dp
    val PADDING_BOTTOM = 60.dp
    val TITLE_FONT_SIZE = 36.sp
}