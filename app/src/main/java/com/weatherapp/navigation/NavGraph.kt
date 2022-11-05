package com.weatherapp.navigation

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.weatherapp.data.local.Constants.CITY_SCREEN
import com.weatherapp.data.local.Constants.CLOUDINESS
import com.weatherapp.data.local.Constants.CONFIRM
import com.weatherapp.data.local.Constants.DETAILS_SCREEN
import com.weatherapp.data.local.Constants.ERROR_SCREEN
import com.weatherapp.data.local.Constants.LATITUDE
import com.weatherapp.data.local.Constants.LONGITUDE
import com.weatherapp.data.local.Constants.TEXT
import com.weatherapp.data.local.Constants.TITLE
import com.weatherapp.data.viewmodel.*
import com.weatherapp.ui.screen.ErrorScreen
import com.weatherapp.ui.screen.city.CityScreen
import com.weatherapp.ui.screen.details.DetailsScreen
import com.weatherapp.ui.screen.home.HomeScreen
import com.weatherapp.ui.screen.search.SearchScreen
import com.weatherapp.ui.screen.settings.SettingsScreen

@ExperimentalMaterialApi
@Composable
fun NavGraph(
    navController: NavHostController,
    homeViewModel: HomeViewModel,
    searchViewModel: SearchViewModel,
    settingsViewModel: SettingsViewModel,
    cityViewModel: CityViewModel,
    detailsViewModel: DetailsViewModel,
    appTheme: MutableState<Boolean>,
) {

    NavHost(
        navController = navController,
        startDestination = BottomBarScreen.Home.route
    ) {

        composable(route = BottomBarScreen.Home.route) {
            HomeScreen(
                viewModel = homeViewModel,
                navController = navController
            )
        }


        composable(route = "$DETAILS_SCREEN/{$LONGITUDE}/{$LATITUDE}/{$CLOUDINESS}",
            arguments = listOf(
                navArgument(name = LONGITUDE) {
                    type = NavType.FloatType
                },
                navArgument(name = LATITUDE) {
                    type = NavType.FloatType
                },
                navArgument(name = CLOUDINESS) {
                    type = NavType.IntType
                }
            )) {
            DetailsScreen(
                viewModel = detailsViewModel,
                longitude = it.arguments?.getFloat(LONGITUDE),
                latitude = it.arguments?.getFloat(LATITUDE),
                cloudiness = it.arguments?.getInt(CLOUDINESS)
            )
        }

        composable(route = BottomBarScreen.Search.route) {
            SearchScreen(
                viewModel = searchViewModel,
                navController = navController
            )
        }
        composable(route = BottomBarScreen.Settings.route) {
            SettingsScreen(
                navController = navController,
                viewModel = settingsViewModel,
                appTheme = appTheme
            )
        }

        composable(route = CITY_SCREEN) {
            CityScreen(
                viewModel = cityViewModel,
                navController = navController
            )
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