package com.weatherapp.ui.screen.search

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
fun SearchScreen() {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(HomeBackground),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Search",
            color = Secondary,
            fontSize = 16.sp
        )
    }
}


@Preview
@Composable
fun SearchScreenPreview() {
    SearchScreen()
}