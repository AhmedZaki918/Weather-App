package com.weatherapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.weatherapp.data.viewmodel.HomeViewModel
import com.weatherapp.data.viewmodel.SearchViewModel
import com.weatherapp.navigation.MainScreen
import com.weatherapp.ui.theme.WeatherAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val homeViewModel: HomeViewModel by viewModels()
    private val searchViewModel: SearchViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeatherAppTheme {
                MainScreen(
                    homeViewModel = homeViewModel,
                    searchViewModel = searchViewModel
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