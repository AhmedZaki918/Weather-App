package com.weatherapp.presentation.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ContentAlpha
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.weatherapp.R
import com.weatherapp.data.local.Constants.DEGREE
import com.weatherapp.data.local.Constants.IMAGE_URL
import com.weatherapp.data.local.Constants.SIZE
import com.weatherapp.data.model.forecast.ListItem
import com.weatherapp.presentation.theme.BIG_MARGIN
import com.weatherapp.presentation.theme.CUSTOM_MARGIN
import com.weatherapp.presentation.theme.LARGE_MARGIN
import com.weatherapp.presentation.theme.MEDIUM_MARGIN
import com.weatherapp.presentation.theme.SMALL_MARGIN
import com.weatherapp.presentation.theme.VERY_SMALL_MARGIN
import com.weatherapp.util.Circle
import com.weatherapp.util.Line
import com.weatherapp.util.LoadingScreen
import com.weatherapp.util.RequestState

@ExperimentalMaterialApi
@Composable
fun SearchScreen(
    viewModel: SearchViewModel,
    uiState: SearchUiState
) {

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .padding(bottom = BIG_MARGIN)
    ) {
        item {
            Header(
                uiState = uiState,
                onTextChange = { newText ->
                    viewModel.onIntent(SearchIntent.UpdateSearchKeyword(newText))
                },
                onSearchClicked = {
                    if (it.isNotEmpty()) {
                        viewModel.onIntent(SearchIntent.Search(it))
                    }
                }
            )
        }

        item {
            CurrentWeather(uiState)
        }

        forecast(uiState)
    }
}


fun LazyListScope.forecast(uiState: SearchUiState) {
    if (uiState.weatherState != RequestState.ERROR) {
        when (uiState.forecastState) {
            RequestState.LOADING -> {

            }

            RequestState.SUCCESS -> {
                val forecast = uiState.weatherForecast.list
                if (!forecast.isNullOrEmpty()) {
                    items(forecast) {
                        ListWeatherForecast(
                            forecast = it,
                            timeVisibility = false
                        )
                    }
                }
            }

            RequestState.ERROR -> {
                item {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Error has been occurred",
                            fontSize = 14.sp,
                            color = MaterialTheme.colors.primaryVariant,
                        )
                    }
                }
            }

            else -> Unit
        }
    }

}


@Composable
fun CurrentWeather(uiState: SearchUiState) {
    when (uiState.weatherState) {
        RequestState.LOADING -> {
            LoadingScreen()
        }

        RequestState.SUCCESS -> {
            WeatherData(uiState)
        }

        RequestState.ERROR -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Error has been occurred",
                    fontSize = 14.sp,
                    color = MaterialTheme.colors.primaryVariant,
                )

            }
        }

        else -> Unit
    }
}


@Composable
fun Header(
    uiState: SearchUiState,
    onTextChange: (String) -> Unit,
    onSearchClicked: (String) -> Unit
) {
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (txtLabel, txtDescription, search) = createRefs()

        Text(
            text = stringResource(R.string.search),
            fontSize = 25.sp,
            color = MaterialTheme.colors.primary,
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
            color = MaterialTheme.colors.primaryVariant,
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
                .background(MaterialTheme.colors.surface),
            text = uiState.searchKeyword,
            onTextChange = { newText ->
                onTextChange(newText)
            },
            onSearchClicked = {
                if (it.isNotEmpty()) {
                    onSearchClicked(it)
                }
            }
        )
    }
}

@Composable
fun ListWeatherForecast(
    forecast: ListItem,
    timeVisibility: Boolean
) {

    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        val (txtDateTime, imageWeather, txtWeather, txtTemp, line) = createRefs()
        val lastIndex = forecast.main?.temp.toString().indexOf(".")

        // Time & Date
        if (timeVisibility) {
            // Display date only in home screen
            Text(
                buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            color = MaterialTheme.colors.primaryVariant,
                            fontSize = 16.sp
                        )
                    ) {
                        append("${forecast.dt_txt?.substring(0, 8)}")
                    }
                    withStyle(
                        style = SpanStyle(
                            color = MaterialTheme.colors.secondary,
                            fontSize = 16.sp
                        )
                    ) {
                        append(forecast.dt_txt?.substring(8, 10).toString())
                    }
                },
                modifier = Modifier
                    .constrainAs(txtDateTime) {
                        start.linkTo(parent.start, LARGE_MARGIN)
                        top.linkTo(parent.top, CUSTOM_MARGIN)
                    }
            )

        } else {
            // Display time and date in details screen
            Text(
                buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            color = MaterialTheme.colors.primaryVariant,
                            fontSize = 14.sp
                        )
                    ) {
                        append("${forecast.dt_txt?.substring(0, 10)}\n")
                    }
                    withStyle(
                        style = SpanStyle(
                            color = MaterialTheme.colors.secondary,
                            fontSize = 16.sp
                        )
                    ) {
                        append(forecast.dt_txt?.substring(11, 16).toString())
                    }
                },
                modifier = Modifier
                    .constrainAs(txtDateTime) {
                        start.linkTo(parent.start, LARGE_MARGIN)
                        top.linkTo(parent.top, CUSTOM_MARGIN)
                    }
            )
        }


        // Weather icon
        Image(
            painter = rememberImagePainter(
                data = "$IMAGE_URL${forecast.weather?.get(0)?.icon}$SIZE"
            ),
            contentDescription = "",
            modifier = Modifier
                .constrainAs(imageWeather) {
                    start.linkTo(txtDateTime.end, CUSTOM_MARGIN)
                    top.linkTo(txtDateTime.top)
                    bottom.linkTo(txtDateTime.bottom)
                }
                .size(50.dp)
        )

        // Description
        Text(
            text = forecast.weather?.get(0)?.description.toString(),
            fontSize = 14.sp,
            color = MaterialTheme.colors.primaryVariant,
            modifier = Modifier
                .constrainAs(txtWeather) {
                    start.linkTo(imageWeather.end, SMALL_MARGIN)
                    top.linkTo(imageWeather.top)
                    bottom.linkTo(imageWeather.bottom)
                }
                .fillMaxWidth(0.2f)
        )

        // Temperature
        Text(
            text = forecast.main?.temp.toString().substring(0, lastIndex) + DEGREE,
            fontSize = 20.sp,
            color = MaterialTheme.colors.primary,
            modifier = Modifier
                .constrainAs(txtTemp) {
                    top.linkTo(txtWeather.top)
                    start.linkTo(txtWeather.end)
                    end.linkTo(parent.end)
                }
        )

        Line(
            modifier = Modifier
                .constrainAs(line) {
                    top.linkTo(txtDateTime.bottom, CUSTOM_MARGIN)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .padding(start = LARGE_MARGIN, end = LARGE_MARGIN)
        )
    }
}

@ExperimentalCoilApi
@Composable
fun WeatherData(
    uiState: SearchUiState
) {
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (txtLocation, txtTemp, txtWeather, icWeather, icWind,
            txtWind, txtVisibility, icVisibility,
            boxHumidityPercentage, txtHumidity, spacer) = createRefs()

        val lastIndex = uiState.currentWeather.main?.temp.toString().indexOf(".")


        Text(
            text = uiState.currentWeather.name.toString(),
            fontSize = 30.sp,
            color = MaterialTheme.colors.primaryVariant,
            modifier = Modifier
                .constrainAs(txtLocation) {
                    start.linkTo(parent.start, LARGE_MARGIN)
                    top.linkTo(parent.top, BIG_MARGIN)
                }
        )

        Text(
            text = "${uiState.currentWeather.main?.temp.toString().substring(0, lastIndex)}°",
            fontFamily = FontFamily.Serif,
            fontSize = 90.sp,
            color = MaterialTheme.colors.primary,
            modifier = Modifier
                .constrainAs(txtTemp) {
                    start.linkTo(parent.start, LARGE_MARGIN)
                    top.linkTo(txtLocation.bottom, SMALL_MARGIN)
                }
        )


        Text(
            text = uiState.currentWeather.weather?.get(0)?.description.toString(),
            fontSize = 18.sp,
            color = MaterialTheme.colors.secondary,
            modifier = Modifier
                .constrainAs(txtWeather) {
                    start.linkTo(txtTemp.start, MEDIUM_MARGIN)
                    top.linkTo(txtTemp.bottom, VERY_SMALL_MARGIN)
                }
        )


        Image(
            painter = rememberImagePainter(
                data = "$IMAGE_URL${uiState.currentWeather.weather?.get(0)?.icon}$SIZE"
            ),
            contentDescription = "",
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
            tint = MaterialTheme.colors.secondary,
            modifier = Modifier
                .constrainAs(icWind) {
                    top.linkTo(txtWeather.bottom, MEDIUM_MARGIN)
                    start.linkTo(icVisibility.start)
                }
        )

        Icon(
            painter = painterResource(id = R.drawable.ic_outline_visibility),
            contentDescription = "",
            tint = MaterialTheme.colors.secondary,
            modifier = Modifier
                .constrainAs(icVisibility) {
                    top.linkTo(icWind.bottom, MEDIUM_MARGIN)
                    end.linkTo(txtVisibility.start, SMALL_MARGIN)
                }
        )


        Text(
            text = "${uiState.windSpeed} ${stringResource(id = R.string.km_h)}",
            fontSize = 16.sp,
            color = MaterialTheme.colors.primaryVariant,
            modifier = Modifier
                .constrainAs(txtWind) {
                    top.linkTo(icWind.top)
                    start.linkTo(icWind.end, SMALL_MARGIN)
                }
        )


        Text(
            text = "${stringResource(id = R.string.visibility)} ${
                uiState.currentWeather.visibility?.div(
                    1000
                )
            } ${
                stringResource(
                    id = R.string.km
                )
            }",
            fontSize = 16.sp,
            color = MaterialTheme.colors.primaryVariant,
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
            if (uiState.currentWeather.main?.humidity != null) {
                uiState.currentWeather.main.humidity.toDouble().div(100).toFloat()
            } else {
                0f
            },
            MaterialTheme.colors.secondary
        )

        Text(
            text = stringResource(R.string.humidity),
            color = MaterialTheme.colors.primaryVariant,
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
    onSearchClicked: (String) -> Unit
) {
    val focusManager = LocalFocusManager.current

    TextField(
        modifier = modifier,
        value = text,
        onValueChange = {
            onTextChange(it)
        },
        placeholder = {
            Text(
                modifier = Modifier
                    .alpha(ContentAlpha.medium),
                text = stringResource(id = R.string.search),
                color = MaterialTheme.colors.primary
            )
        },
        textStyle = TextStyle(color = MaterialTheme.colors.primaryVariant),
        singleLine = true,
        leadingIcon = {
            IconButton(modifier = Modifier.alpha(ContentAlpha.disabled),
                onClick = {
                    onSearchClicked(text)
                    focusManager.clearFocus()
                }) {
                Icon(
                    imageVector = Icons.Filled.Search,
                    tint = MaterialTheme.colors.primaryVariant,
                    contentDescription = ""
                )
            }
        },
        trailingIcon = {
            IconButton(modifier = Modifier.alpha(ContentAlpha.disabled),
                onClick = {
                    if (text.isNotEmpty()) {
                        onTextChange("")
                    } else focusManager.clearFocus()
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.Close,
                    tint = MaterialTheme.colors.primaryVariant,
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
                focusManager.clearFocus()
            }
        )
    )
}
