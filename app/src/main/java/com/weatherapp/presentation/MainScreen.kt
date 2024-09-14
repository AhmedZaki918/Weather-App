package com.weatherapp.presentation

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.weatherapp.R
import com.weatherapp.data.local.Constants.HOME
import com.weatherapp.data.local.Constants.SEARCH
import com.weatherapp.data.local.Constants.WISHLIST
import com.weatherapp.presentation.navigation.BottomBarScreen
import com.weatherapp.presentation.navigation.NavGraph
import com.weatherapp.presentation.city.CityViewModel
import com.weatherapp.presentation.details.DetailsViewModel
import com.weatherapp.presentation.home.HomeViewModel
import com.weatherapp.presentation.search.SearchViewModel
import com.weatherapp.presentation.settings.SettingsViewModel


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@ExperimentalMaterialApi
@Composable
fun MainScreen(
    homeViewModel: HomeViewModel,
    searchViewModel: SearchViewModel,
    settingsViewModel: SettingsViewModel,
    cityViewModel: CityViewModel,
    detailsViewModel: DetailsViewModel,
    appTheme: MutableState<Boolean>
) {

    val navController = rememberNavController()
    Scaffold(
        bottomBar = { BottomBar(navController = navController) }
    ) {
        NavGraph(
            navController = navController,
            homeViewModel = homeViewModel,
            searchViewModel = searchViewModel,
            settingsViewModel = settingsViewModel,
            cityViewModel = cityViewModel,
            detailsViewModel = detailsViewModel,
            appTheme = appTheme
        )
    }
}

@Composable
fun BottomBar(navController: NavHostController) {
    val screens = listOf(
        BottomBarScreen.Home,
        BottomBarScreen.Search,
        BottomBarScreen.Settings,
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    var bottomBarState by rememberSaveable { (mutableStateOf(true)) }

    bottomBarState = when (currentDestination?.route) {
        BottomBarScreen.Home.route -> true
        BottomBarScreen.Search.route -> true
        BottomBarScreen.Settings.route -> true
        else -> false
    }


    AnimatedVisibility(visible = bottomBarState,
        content = {
            BottomNavigation(
                backgroundColor = MaterialTheme.colors.onPrimary,
                contentColor = MaterialTheme.colors.primary
            ) {
                screens.forEach { screen ->
                    AddItem(
                        screen = screen,
                        currentDestination = currentDestination,
                        navController = navController,
                    )
                }
            }
        })
}

@Composable
fun RowScope.AddItem(
    screen: BottomBarScreen,
    currentDestination: NavDestination?,
    navController: NavHostController
) {

    BottomNavigationItem(
        label = {
            Text(
                text = when (screen.route) {
                    HOME -> stringResource(id = R.string.home)
                    SEARCH -> stringResource(id = R.string.search)
                    WISHLIST -> stringResource(id = R.string.wishlist)
                    else -> stringResource(id = R.string.settings)
                }
            )
        },
        alwaysShowLabel = false,
        icon = {
            Icon(
                imageVector = screen.icon,
                contentDescription = "Navigation Icon"
            )
        },
        selected = currentDestination?.hierarchy?.any {
            it.route == screen.route
        } == true,
        onClick = {
            navController.navigate(screen.route) {
                popUpTo(navController.graph.findStartDestination().id)
                launchSingleTop = true
            }
        }
    )
}