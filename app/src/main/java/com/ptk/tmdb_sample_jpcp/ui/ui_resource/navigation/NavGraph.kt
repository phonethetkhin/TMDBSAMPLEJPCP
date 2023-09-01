package com.ptk.tmdb_sample_jpcp.ui.ui_resource.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.ptk.tmdb_sample_jpcp.ui.screen.HomeScreen

@Composable
fun NavGraph(
    navController: NavHostController,
) {
    NavHost(navController = navController, startDestination = Routes.HomeScreen.route) {
        composable(route = Routes.HomeScreen.route) {
            HomeScreen(navController)
        }


    }
}