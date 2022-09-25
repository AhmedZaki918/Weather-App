package com.weatherapp.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.weatherapp.data.local.Constants.CITY_SCREEN
import com.weatherapp.data.local.Constants.ERROR_SCREEN
import com.weatherapp.data.viewmodel.CityViewModel
import com.weatherapp.data.viewmodel.HomeViewModel
import com.weatherapp.data.viewmodel.SearchViewModel
import com.weatherapp.data.viewmodel.SettingsViewModel
import com.weatherapp.ui.theme.StatusBar


@ExperimentalMaterialApi
@Composable
fun MainScreen(
    homeViewModel: HomeViewModel,
    searchViewModel: SearchViewModel,
    settingsViewModel: SettingsViewModel,
    cityViewModel: CityViewModel
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
            cityViewModel = cityViewModel
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

    when (currentDestination?.route) {
        BottomBarScreen.Home.route -> bottomBarState = true
        BottomBarScreen.Search.route -> bottomBarState = true
        BottomBarScreen.Settings.route -> bottomBarState = true
        ERROR_SCREEN -> bottomBarState = false
        CITY_SCREEN -> bottomBarState = false
    }


    AnimatedVisibility(visible = bottomBarState,
        content = {
            BottomNavigation(
                backgroundColor = StatusBar,
                contentColor = Color.White
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
            Text(text = screen.title)
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


@Preview(showSystemUi = true)
@Composable
fun MainScreenPreview() {
}