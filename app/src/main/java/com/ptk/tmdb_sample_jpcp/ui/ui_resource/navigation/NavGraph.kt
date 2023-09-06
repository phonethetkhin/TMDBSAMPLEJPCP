package com.ptk.tmdb_sample_jpcp.ui.ui_resource.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.ptk.tmdb_sample_jpcp.ui.screen.DetailScreen
import com.ptk.tmdb_sample_jpcp.ui.screen.HomeScreen
import com.ptk.tmdb_sample_jpcp.viewmodel.HomeViewModel

@Composable
fun NavGraph(
    navController: NavHostController,
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    NavHost(navController = navController, startDestination = Routes.HomeScreen.route) {
        composable(route = Routes.HomeScreen.route) {
            HomeScreen(navController, homeViewModel)
        }

        composable(route = Routes.DetailScreen.route + "/movieId={movieId}/isFav={isFav}/status={status}",
            arguments = listOf(
                navArgument("movieId") {
                    type = NavType.IntType
                },
                navArgument("isFav") {
                    type = NavType.BoolType
                },
                navArgument("status") {
                    type = NavType.IntType
                }

            )) { nav ->
            val movieId = nav.arguments?.getInt("movieId")!!
            val isFav = nav.arguments?.getBoolean("isFav")!!
            val status = nav.arguments?.getInt("status")!!

            DetailScreen(navController, movieId, homeViewModel, isFav,status)
        }


    }
}