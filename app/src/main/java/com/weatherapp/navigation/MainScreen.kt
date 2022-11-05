package com.weatherapp.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.weatherapp.R
import com.weatherapp.data.local.Constants
import com.weatherapp.data.local.Constants.CITY_SCREEN
import com.weatherapp.data.local.Constants.DETAILS_SCREEN
import com.weatherapp.data.local.Constants.ERROR_SCREEN
import com.weatherapp.data.local.Constants.HOME
import com.weatherapp.data.local.Constants.LATITUDE
import com.weatherapp.data.local.Constants.LONGITUDE
import com.weatherapp.data.local.Constants.SEARCH
import com.weatherapp.data.local.Constants.WISHLIST
import com.weatherapp.data.viewmodel.*


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

    when (currentDestination?.route) {
        BottomBarScreen.Home.route -> bottomBarState = true
        BottomBarScreen.Search.route -> bottomBarState = true
        BottomBarScreen.Settings.route -> bottomBarState = true
        "$ERROR_SCREEN/{${Constants.TITLE}}/{${Constants.TEXT}}/{${Constants.CONFIRM}}" -> {
            bottomBarState = false
        }
        CITY_SCREEN -> bottomBarState = false
        "$DETAILS_SCREEN/{$LONGITUDE}/{$LATITUDE}/{${Constants.CLOUDINESS}}" -> bottomBarState = false
    }


    AnimatedVisibility(visible = bottomBarState,
        content = {
            BottomNavigation(
                backgroundColor = MaterialTheme.colors.surface,
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


@Preview(showSystemUi = true)
@Composable
fun MainScreenPreview() {
}