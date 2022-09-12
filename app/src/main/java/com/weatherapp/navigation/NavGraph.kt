package com.weatherapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.weatherapp.data.local.Constants.CONFIRM
import com.weatherapp.data.local.Constants.ERROR_SCREEN
import com.weatherapp.data.local.Constants.TEXT
import com.weatherapp.data.local.Constants.TITLE
import com.weatherapp.data.viewmodel.HomeViewModel
import com.weatherapp.data.viewmodel.SearchViewModel
import com.weatherapp.ui.screen.ErrorScreen
import com.weatherapp.ui.screen.home.HomeScreen
import com.weatherapp.ui.screen.search.SearchScreen
import com.weatherapp.ui.screen.settings.SettingsScreen

@Composable
fun NavGraph(
    navController: NavHostController,
    homeViewModel: HomeViewModel,
    searchViewModel: SearchViewModel
) {

    NavHost(
        navController = navController,
        startDestination = BottomBarScreen.Home.route
    ) {

        composable(route = BottomBarScreen.Home.route) {
            HomeScreen(
                viewModel = homeViewModel
            )
        }
        composable(route = BottomBarScreen.Search.route) {
            SearchScreen(
                viewModel = searchViewModel,
                navController = navController
            )
        }
        composable(route = BottomBarScreen.Settings.route) {
            SettingsScreen()
        }

        composable(
            route = "$ERROR_SCREEN/{$TITLE}/{$TEXT}/{$CONFIRM}",
            arguments = listOf(
                navArgument(name = TITLE) {
                    type = NavType.StringType
                },
                navArgument(name = TEXT) {
                    type = NavType.StringType
                },
                navArgument(name = CONFIRM) {
                    type = NavType.StringType
                }
            )
        ) {
            ErrorScreen(
                title = it.arguments?.getString(TITLE),
                text = it.arguments?.getString(TEXT),
                confirmButton = it.arguments?.getString(CONFIRM)
            ) { navController.navigateUp() }
        }
    }
}