package com.luisma.conexiones.android.core_ui.theme

import androidx.compose.runtime.Stable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color


@Stable
data class CThemeColors(
    val screenBackground: Color,
    val textDefault: Color,
    val iconDefault: Color,
    val softContrastScreenBackground: Color,
) {

    companion object {
        fun light(): CThemeColors {
            return CThemeColors(
                screenBackground = CColor.white2,
                textDefault = CColor.black,
                iconDefault = CColor.black,
                softContrastScreenBackground = CColor.white
            )
        }

        fun dark(): CThemeColors {
            return CThemeColors(
                screenBackground = CColor.grey,
                textDefault = CColor.white2,
                iconDefault = CColor.white2,
                softContrastScreenBackground = CColor.black
            )
        }
    }

}

object CColor {
    val black = Color(0XFF1D2B36)
    val green = Color(0XFF63E6BE)
    val grey = Color(0XFF454F60)
    val orange = Color(0XFFE87800)
    val red = Color(0XFFC5003B)
    val white = Color(0XFFFFFFFF)
    val white2 = Color(0XFFF0F1F3)
    val yellow = Color(0XFFFFD43B)
}


val LocalCColors = staticCompositionLocalOf { CThemeColors.light() }