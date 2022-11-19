package com.weatherapp

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import com.weatherapp.data.local.Constants.DARK_THEME
import com.weatherapp.data.viewmodel.*
import com.weatherapp.navigation.MainScreen
import com.weatherapp.ui.theme.WeatherAppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@ExperimentalMaterialApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val homeViewModel: HomeViewModel by viewModels()
    private val searchViewModel: SearchViewModel by viewModels()
    private val settingsViewModel: SettingsViewModel by viewModels()
    private val cityViewModel: CityViewModel by viewModels()
    private val detailsViewModel: DetailsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContent {
            // Read user app theme from DataStore
            val appTheme = remember { mutableStateOf(true) }
            LaunchedEffect(key1 = true) {
                settingsViewModel.retrieveBoolean(DARK_THEME).collectLatest {
                    appTheme.value = it
                }
            }

            WeatherAppTheme(appTheme.value) {
                MainScreen(
                    homeViewModel = homeViewModel,
                    searchViewModel = searchViewModel,
                    settingsViewModel = settingsViewModel,
                    cityViewModel = cityViewModel,
                    detailsViewModel = detailsViewModel,
                    appTheme = appTheme
                )
            }
        }
    }
}


@Preview(showSystemUi = true)
@Composable
fun DefaultPreview() {
    WeatherAppTheme {}
}