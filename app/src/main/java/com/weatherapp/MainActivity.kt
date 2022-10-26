package com.weatherapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import com.weatherapp.data.local.Constants.DARK_THEME
import com.weatherapp.data.viewmodel.CityViewModel
import com.weatherapp.data.viewmodel.HomeViewModel
import com.weatherapp.data.viewmodel.SearchViewModel
import com.weatherapp.data.viewmodel.SettingsViewModel
import com.weatherapp.navigation.MainScreen
import com.weatherapp.ui.theme.WeatherAppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@ExperimentalMaterialApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val homeViewModel: HomeViewModel by viewModels()
    private val searchViewModel: SearchViewModel by viewModels()
    private val settingsViewModel: SettingsViewModel by viewModels()
    private val cityViewModel: CityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
                    settingsViewModel= settingsViewModel,
                    cityViewModel = cityViewModel,
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