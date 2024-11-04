package com.luisma.conexiones.android.router

import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.luisma.conexiones.android.features.game.GameViewBuilder
import com.luisma.conexiones.android.features.levels.LevelsBuilder
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun Router(
    routerService: RouterService,
) {
    val navController = rememberNavController()
    val navigationCoroutine = rememberCoroutineScope()

    NavHost(
        navController = navController,
        startDestination = Routes.Levels.routeName,
    ) {
        navigationCoroutine.launch {
            routerService.router.collectLatest { routePayload ->
                when (routePayload.route) {
                    Routes.Levels -> navController.navigate(route = routePayload.route.routeName)
                    Routes.Game -> {
                        navController.navigate(route = "${routePayload.route.routeName}/${routePayload.payload}")
                    }

                    Routes.Back -> navController.popBackStack()
                    Routes.BackWithRefresh -> {
                        navController.previousBackStackEntry?.savedStateHandle?.set(
                            routePayload.route.routeName,
                            value = routePayload.payload
                        )
                        navController.popBackStack()
                    }

                }
            }
        }

        composable(route = Routes.Levels.routeName) { backStackEntry ->
            val resetFlag = backStackEntry.savedStateHandle
                .get<String>(Routes.BackWithRefresh.routeName)
            LevelsBuilder(
                resetFlag = resetFlag ?: ""
            )
        }

        composable(
            route = "${Routes.Game.routeName}/{${Routes.Game.payloadName}}",
            arguments = listOf(navArgument(Routes.Game.payloadName!!) {
                type = NavType.StringType
            })
        ) { backStackEntry ->
            val gameId = backStackEntry.arguments?.getString(Routes.Game.payloadName)
            GameViewBuilder(gameId = gameId?.toInt() ?: 0)
        }

    }
}