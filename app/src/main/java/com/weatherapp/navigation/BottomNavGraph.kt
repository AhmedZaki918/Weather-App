package com.weatherapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.weatherapp.MainActivity
import com.weatherapp.data.viewmodel.HomeViewModel
import com.weatherapp.ui.screen.home.HomeScreen
import com.weatherapp.ui.screen.search.SearchScreen
import com.weatherapp.ui.screen.settings.SettingsScreen

@Composable
fun BottomNavGraph(
    navController: NavHostController,
    viewModel: HomeViewModel,
    mainActivity: MainActivity
) {

    NavHost(
        navController = navController,
        startDestination = BottomBarScreen.Home.route
    ) {

        composable(route = BottomBarScreen.Home.route) {
            HomeScreen(
                viewModel = viewModel,
                mainActivity = mainActivity
            )
        }
        composable(route = BottomBarScreen.Search.route) {
            SearchScreen()
        }
        composable(route = BottomBarScreen.Settings.route) {
            SettingsScreen()
        }
    }
}