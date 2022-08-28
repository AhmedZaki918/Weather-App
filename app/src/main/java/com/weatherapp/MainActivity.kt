package com.weatherapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.weatherapp.ui.screen.home.HomeScreen
import com.weatherapp.ui.theme.WeatherAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeatherAppTheme {
                HomeScreen()
            }
        }
    }
}


@Preview(showSystemUi = true)
@Composable
fun DefaultPreview() {
    WeatherAppTheme {
        HomeScreen()
    }
}