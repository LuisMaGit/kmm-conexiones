package com.luisma.conexiones.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import com.luisma.conexiones.android.core_ui.theme.CTheme
import com.luisma.conexiones.android.core_ui.theme.CThemeProvider
import com.luisma.conexiones.android.features.game.GameViewBuilder
import com.luisma.conexiones.android.router.Router
import com.luisma.conexiones.android.router.RouterService

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CThemeProvider {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = CTheme.colors.screenBackground),
                ) {
                    Router(
                        routerService = RouterService
                    )
                }
            }
        }
    }
}
