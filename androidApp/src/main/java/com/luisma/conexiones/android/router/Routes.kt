package com.luisma.conexiones.android.router

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow


sealed class Routes(
    val routeName: String,
    val payloadName: String? = null
) {
    data object Back : Routes(routeName = "back")

    data object Levels : Routes(routeName = "levels")

    data object Game : Routes(
        routeName = "game",
        payloadName = "game_id"
    )
}

data class RoutePayload(
    val route: Routes,
    val payload: String? = null
)

object RouterService {
    private val _router = MutableSharedFlow<RoutePayload>()
    val router: SharedFlow<RoutePayload> = _router

    suspend fun goTo(route: RoutePayload) {
        _router.emit(route)
    }

    suspend fun goBack() {
        _router.emit(RoutePayload(route = Routes.Back))
    }
}