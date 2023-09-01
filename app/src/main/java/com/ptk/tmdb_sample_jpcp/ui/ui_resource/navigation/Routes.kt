package com.ptk.tmdb_sample_jpcp.ui.ui_resource.navigation

sealed class Routes(val route: String) {
    data object SplashScreen : Routes("/splash_screen")
    data object LoginScreen : Routes("/login_screen")
    data object HomeScreen : Routes("/home_screen")
}