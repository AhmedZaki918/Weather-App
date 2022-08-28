package com.weatherapp.ui.screen.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.weatherapp.ui.theme.HomeBackground
import com.weatherapp.ui.theme.Secondary

@Composable
fun SettingsScreen() {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(HomeBackground),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Settings",
            color = Secondary,
            fontSize = 16.sp
        )
    }
}


@Preview
@Composable
fun SettingsScreenPreview() {
    SettingsScreen()
}