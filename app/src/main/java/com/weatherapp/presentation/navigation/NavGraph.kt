package com.weatherapp.presentation.navigation

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
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
import com.weatherapp.presentation.ErrorScreen
import com.weatherapp.presentation.city.CityScreen
import com.weatherapp.presentation.city.CityViewModel
import com.weatherapp.presentation.details.DetailsScreen
import com.weatherapp.presentation.details.DetailsViewModel
import com.weatherapp.presentation.home.HomeScreen
import com.weatherapp.presentation.home.HomeViewModel
import com.weatherapp.presentation.search.SearchScreen
import com.weatherapp.presentation.search.SearchViewModel
import com.weatherapp.presentation.settings.SettingsScreen
import com.weatherapp.presentation.settings.SettingsViewModel

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
            val uiState = searchViewModel.uiState.collectAsState().value
            SearchScreen(
                uiState = uiState,
                viewModel = searchViewModel
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