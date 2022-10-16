package com.weatherapp.ui.screen.search

import androidx.compose.foundation.Image
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.weatherapp.R
import com.weatherapp.data.local.Constants.ERROR_SCREEN
import com.weatherapp.data.local.Constants.IMAGE_URL
import com.weatherapp.data.local.Constants.SIZE
import com.weatherapp.data.model.forecast.FiveDaysForecastResponse
import com.weatherapp.data.model.forecast.ListItem
import com.weatherapp.data.model.geocoding.GeocodingResponse
import com.weatherapp.data.model.weather.CurrentWeatherResponse
import com.weatherapp.data.network.Resource
import com.weatherapp.data.viewmodel.SearchViewModel
import com.weatherapp.ui.theme.*
import com.weatherapp.util.Circle
import com.weatherapp.util.RequestState
import com.weatherapp.util.handleApiError

@ExperimentalMaterialApi
@Composable
fun SearchScreen(
    viewModel: SearchViewModel,
    navController: NavHostController
) {

    var windSpeed: Int? = null
    var forecast: List<ListItem> = listOf()
    var currentWeather: CurrentWeatherResponse? = null
    val context = LocalContext.current


    if (viewModel.requestState.value == RequestState.COMPLETE) {

        val geocodingResponse by viewModel.geocoding.collectAsState()
        val weatherResponse by viewModel.currentWeather.collectAsState()
        val forecastResponse by viewModel.weatherForecast.collectAsState()

        if (geocodingResponse is Resource.Success) {
            val response = geocodingResponse as Resource.Success<List<GeocodingResponse>>
            response.value.also { geocoding ->
                if (geocoding.isNotEmpty()) {
                    val lat = geocoding[0].lat ?: 0.0
                    val lon = geocoding[0].lon ?: 0.0
                    viewModel.apply {
                        initCurrentWeather(lat, lon, tempUnit)
                        initFiveDaysForecast(lat, lon, tempUnit)
                    }
                } else {
                    DisplayInvalidSearch(navController, viewModel)
                }
            }
        } else if (geocodingResponse is Resource.Failure) {
            context.handleApiError(geocodingResponse as Resource.Failure)
            viewModel.requestState.value = RequestState.IDLE
        }


        if (weatherResponse is Resource.Success) {
            currentWeather = (weatherResponse as Resource.Success<CurrentWeatherResponse>).value
            windSpeed = currentWeather.wind?.speed?.times(60)?.times(60)?.div(1000)?.toInt()
        } else if (weatherResponse is Resource.Failure) {
            context.handleApiError(weatherResponse as Resource.Failure)
        }


        if (forecastResponse is Resource.Success) {
            forecast = (forecastResponse as Resource.Success<FiveDaysForecastResponse>).value.list!!
        } else if (forecastResponse is Resource.Failure) {
            context.handleApiError(forecastResponse as Resource.Failure)
        }
    }


    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .padding(bottom = BIG_MARGIN)
    ) {
        item {
            Header(viewModel, currentWeather, windSpeed)
        }

        items(forecast) {
            ListWeatherForecast(forecast = it)
        }
    }
}


@ExperimentalCoilApi
@Composable
fun Header(
    viewModel: SearchViewModel,
    currentWeather: CurrentWeatherResponse?,
    windSpeed: Int?
) {

    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (txtLabel, txtDescription, search, txtLocation,
            txtTemp, txtWeather, icWeather, icWind,
            txtWind, txtVisibility, icVisibility,
            boxHumidityPercentage, txtHumidity, spacer) = createRefs()

        val searchTextState by viewModel.searchTextState

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
            text = searchTextState,
            onTextChange = { newText ->
                viewModel.searchTextState.value = newText
            },
            onSearchClicked = {
                if (it.isNotEmpty()) {
                    viewModel.initGeocoding(it)
                }
            }
        )


        if (currentWeather != null) {
            val lastIndex = currentWeather.main?.temp.toString().indexOf(".")


            Text(
                text = currentWeather.name.toString(),
                fontSize = 30.sp,
                color = MaterialTheme.colors.primaryVariant,
                modifier = Modifier
                    .constrainAs(txtLocation) {
                        start.linkTo(parent.start, LARGE_MARGIN)
                        top.linkTo(search.bottom, BIG_MARGIN)
                    }
            )

            Text(
                text = "${currentWeather.main?.temp.toString().substring(0, lastIndex)}°",
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
                text = currentWeather.weather?.get(0)?.description.toString(),
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
                    data = "$IMAGE_URL${currentWeather.weather?.get(0)?.icon}$SIZE"
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
                text = "$windSpeed ${stringResource(id = R.string.km_h)}",
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
                    currentWeather.visibility?.div(
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
                if (currentWeather.main?.humidity != null) {
                    currentWeather.main.humidity.toDouble().div(100).toFloat()
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


@Composable
fun DisplayInvalidSearch(
    navController: NavHostController,
    viewModel: SearchViewModel
) {
    navController.navigate(
        "$ERROR_SCREEN/${stringResource(id = R.string.error)}/${
            stringResource(
                id = R.string.invalid_city
            )
        }/${stringResource(id = R.string.ok)}"
    )
    viewModel.requestState.value = RequestState.IDLE
}
