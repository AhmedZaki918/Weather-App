package com.weatherapp.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import com.weatherapp.data.local.Constants.HOME
import com.weatherapp.data.local.Constants.SEARCH
import com.weatherapp.data.local.Constants.SETTINGS
import com.weatherapp.data.local.Constants.WISHLIST

sealed class BottomBarScreen(
    val route: String,
    val icon: ImageVector
) {

    data object Home : BottomBarScreen(
        route = HOME,
        icon = Icons.Default.Home
    )

    data object Search : BottomBarScreen(
        route = SEARCH,
        icon = Icons.Default.Search
    )

    data object Wishlist: BottomBarScreen(
        route = WISHLIST,
        icon = Icons.Default.Favorite
    )

    data object Settings : BottomBarScreen(
        route = SETTINGS,
        icon = Icons.Default.Settings
    )
}
