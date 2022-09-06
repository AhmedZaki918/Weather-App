package com.weatherapp.ui.screen.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.weatherapp.Circle
import com.weatherapp.R
import com.weatherapp.data.viewmodel.HomeViewModel
import com.weatherapp.ui.theme.*

@Composable
fun SearchScreen(viewModel: HomeViewModel) {

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(HomeBackground)
            .padding(bottom = BIG_MARGIN)
    ) {
        item {
            Header()
        }
        items(viewModel.displayDummy()) {
            ListWeatherForecast(forecast = it)
        }
    }
}

@Composable
fun Header() {

    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (txtLabel, txtDescription, search, txtLocation, txtTemp, txtWeather, icWeather, icWind, txtWind,
            txtVisibility, icVisibility, boxHumidityPercentage, txtHumidity, spacer) = createRefs()

        Text(
            text = stringResource(R.string.search),
            fontSize = 25.sp,
            color = Color.White,
            modifier = Modifier
                .constrainAs(txtLabel) {
                    top.linkTo(parent.top, LARGE_MARGIN)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        )

        Text(
            text = stringResource(R.string.search_caption),
            fontSize = 16.sp,
            lineHeight = 25.sp,
            textAlign = TextAlign.Center,
            color = Secondary,
            modifier = Modifier
                .constrainAs(txtDescription) {
                    top.linkTo(txtLabel.bottom, MEDIUM_MARGIN)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .padding(start = LARGE_MARGIN, end = LARGE_MARGIN)
        )


        SearchBar(
            modifier = Modifier
                .constrainAs(search) {
                    top.linkTo(txtDescription.bottom, LARGE_MARGIN)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .background(StatusBar),
            text = "",
            onTextChange = {},
            onCloseClicked = {},
            onSearchClicked = {}
        )

        Text(
            text = "Cairo",
            fontSize = 30.sp,
            color = Secondary,
            modifier = Modifier
                .constrainAs(txtLocation) {
                    start.linkTo(parent.start, LARGE_MARGIN)
                    top.linkTo(search.bottom, BIG_MARGIN)
                }
        )

        Text(
            text = "18°",
            fontFamily = FontFamily.Serif,
            fontSize = 90.sp,
            color = Color.White,
            modifier = Modifier
                .constrainAs(txtTemp) {
                    start.linkTo(parent.start, LARGE_MARGIN)
                    top.linkTo(txtLocation.bottom, SMALL_MARGIN)
                }
        )

        Text(
            text = "Clear",
            fontSize = 18.sp,
            color = Hint,
            modifier = Modifier
                .constrainAs(txtWeather) {
                    start.linkTo(txtTemp.start, MEDIUM_MARGIN)
                    top.linkTo(txtTemp.bottom, VERY_SMALL_MARGIN)
                }
        )

        Icon(
            painter = painterResource(id = R.drawable.preview_cloudy),
            contentDescription = "",
            tint = Secondary,
            modifier = Modifier
                .constrainAs(icWeather) {
                    top.linkTo(txtTemp.top)
                    bottom.linkTo(txtTemp.bottom)
                    start.linkTo(txtTemp.end)
                    end.linkTo(parent.end)
                }
                .size(100.dp)
        )

        Icon(
            painter = painterResource(id = R.drawable.ic_outline_wind),
            contentDescription = "",
            tint = Hint,
            modifier = Modifier
                .constrainAs(icWind) {
                    top.linkTo(txtWeather.bottom, MEDIUM_MARGIN)
                    start.linkTo(icVisibility.start)
                }
        )

        Text(
            text = "16 km / h",
            fontSize = 16.sp,
            color = Secondary,
            modifier = Modifier
                .constrainAs(txtWind) {
                    top.linkTo(icWind.top)
                    start.linkTo(icWind.end, SMALL_MARGIN)
                }
        )

        Icon(
            painter = painterResource(id = R.drawable.ic_outline_visibility),
            contentDescription = "",
            tint = Hint,
            modifier = Modifier
                .constrainAs(icVisibility) {
                    top.linkTo(icWind.bottom, MEDIUM_MARGIN)
                    end.linkTo(txtVisibility.start, SMALL_MARGIN)
                }
        )

        Text(
            text = "Visibility 10 km",
            fontSize = 16.sp,
            color = Secondary,
            modifier = Modifier
                .constrainAs(txtVisibility) {
                    top.linkTo(icVisibility.top)
                    end.linkTo(parent.end, LARGE_MARGIN)
                }
        )


        Circle(
            modifier = Modifier
                .constrainAs(boxHumidityPercentage) {
                    top.linkTo(icWind.bottom, LARGE_MARGIN)
                    start.linkTo(parent.start, LARGE_MARGIN)
                },
            0.45f
        )

        Text(
            text = stringResource(R.string.humidity),
            color = Secondary,
            fontSize = 16.sp,
            modifier = Modifier.constrainAs(txtHumidity) {
                start.linkTo(boxHumidityPercentage.end, MEDIUM_MARGIN)
                top.linkTo(boxHumidityPercentage.top)
                bottom.linkTo(boxHumidityPercentage.bottom)
            }
        )


        Spacer(
            modifier = Modifier
                .constrainAs(spacer) {
                    top.linkTo(boxHumidityPercentage.bottom, LARGE_MARGIN)
                    start.linkTo(parent.start)
                })
    }
}


@Composable
fun SearchBar(
    modifier: Modifier,
    text: String,
    onTextChange: (String) -> Unit,
    onCloseClicked: () -> Unit,
    onSearchClicked: (String) -> Unit
) {

    var userText by remember {
        mutableStateOf("")
    }

    TextField(
        modifier = modifier,
        value = userText,
        onValueChange = {
            userText = it
        },
        placeholder = {
            Text(
                modifier = Modifier
                    .alpha(ContentAlpha.medium),
                text = "Search",
                color = Color.White
            )
        },
        singleLine = true,
        leadingIcon = {
            IconButton(modifier = Modifier.alpha(ContentAlpha.disabled),
                onClick = { }) {
                Icon(
                    imageVector = Icons.Filled.Search,
                    tint = Secondary,
                    contentDescription = ""
                )
            }
        },
        trailingIcon = {
            IconButton(modifier = Modifier.alpha(ContentAlpha.disabled),
                onClick = {
                    if (text.isNotEmpty()) onTextChange("") else onCloseClicked()
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.Close,
                    tint = Secondary,
                    contentDescription = ""
                )
            }
        },
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Search
        ),
        keyboardActions = KeyboardActions(
            onSearch = {
                onSearchClicked(text)
            }
        )
    )
}


@Preview(showSystemUi = true)
@Composable
fun SearchScreenPreview() {
}