package com.weatherapp.ui.screen.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.weatherapp.R
import com.weatherapp.data.local.Constants.C_UNIT
import com.weatherapp.data.local.Constants.DETAILS_SCREEN
import com.weatherapp.data.local.Constants.FORMAT_TYPE
import com.weatherapp.data.local.Constants.F_UNIT
import com.weatherapp.data.local.Constants.IMAGE_URL
import com.weatherapp.data.local.Constants.METRIC
import com.weatherapp.data.local.Constants.SIZE
import com.weatherapp.data.model.forecast.FiveDaysForecastResponse
import com.weatherapp.data.model.forecast.ListItem
import com.weatherapp.data.model.geocoding.GeocodingResponse
import com.weatherapp.data.model.weather.CurrentWeatherResponse
import com.weatherapp.data.network.Resource
import com.weatherapp.data.viewmodel.HomeViewModel
import com.weatherapp.ui.theme.*
import com.weatherapp.util.Circle
import com.weatherapp.util.RequestState
import com.weatherapp.util.formatDate
import com.weatherapp.util.handleApiError

@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    navController: NavHostController
) {

    val apiError = remember {
        mutableStateOf("")
    }

    viewModel.requestState.value.also { state ->
        when (state) {
            RequestState.IDLE -> {
                LoadingScreen()
            }
            RequestState.COMPLETE -> {
                UpdateUi(
                    viewModel,
                    apiError,
                    navController
                )
            }
            RequestState.ERROR -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colors.background)
                        .padding(start = LARGE_MARGIN, end = LARGE_MARGIN),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = apiError.value,
                        color = MaterialTheme.colors.primaryVariant,
                        fontSize = 20.sp
                    )
                }
            }
        }
    }
}


@Composable
fun LoadingScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}


@ExperimentalCoilApi
@Composable
fun UpdateUi(
    viewModel: HomeViewModel,
    apiError: MutableState<String>,
    navController: NavHostController
) {

    val context = LocalContext.current
    val scrollState = rememberScrollState()
    var data: CurrentWeatherResponse? = null
    var forecast: List<ListItem> = listOf()
    var windSpeed: Int? = null
    var latitude = 0.0
    var longitude = 0.0

    ConstraintLayout(
        modifier = Modifier
            .background(MaterialTheme.colors.background)
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(bottom = 70.dp)
    ) {

        val (
            icLocation, txtLocation,
            icWind, txtWind, txtVisibility, icVisibility,
            boxHumidityPercentage, txtHumidity, txtDate, lrWeather, coContainer, btnMore
        ) = createRefs()

        val geocodingResponse by viewModel.geocoding.collectAsState()
        val weatherResponse by viewModel.currentWeather.collectAsState()
        val forecastResponse by viewModel.weatherForecast.collectAsState()


        if (geocodingResponse is Resource.Success) {
            val response = geocodingResponse as Resource.Success<List<GeocodingResponse>>
            response.value.also { geocoding ->
                if (geocoding.isNotEmpty()) {
                    latitude = geocoding[0].lat ?: 0.0
                    longitude = geocoding[0].lon ?: 0.0

                    LaunchedEffect(key1 = true) {
                        viewModel.initCurrentWeather(
                            latitude,
                            longitude,
                            viewModel.tempUnit
                        )
                    }

                } else {
                    apiError.value = context.getString(R.string.invalid_city)
                    viewModel.requestState.value = RequestState.ERROR
                }
            }
        } else if (geocodingResponse is Resource.Failure) {
            context.handleApiError(geocodingResponse as Resource.Failure)
            apiError.value = context.getString(R.string.connection_error)
            viewModel.requestState.value = RequestState.ERROR
        }


        if (weatherResponse is Resource.Success) {
            data = (weatherResponse as Resource.Success<CurrentWeatherResponse>).value
            windSpeed = data?.wind?.speed?.times(60)?.times(60)?.div(1000)?.toInt()
        } else if (weatherResponse is Resource.Failure) {
            context.handleApiError(weatherResponse as Resource.Failure)
        }


        if (forecastResponse is Resource.Success) {
            forecast = (forecastResponse as Resource.Success<FiveDaysForecastResponse>).value.list!!
        } else if (forecastResponse is Resource.Failure) {
            LaunchedEffect(key1 = true) {
                context.handleApiError(forecastResponse as Resource.Failure)
            }
        }


        if (data != null) {
            val unitLetter = if (viewModel.tempUnit == METRIC) C_UNIT else F_UNIT
            val lastIndex = data?.main?.temp.toString().indexOf(".")


            Text(
                text = data?.name.toString(),
                color = MaterialTheme.colors.primary,
                fontSize = 18.sp,
                modifier = Modifier
                    .constrainAs(txtLocation) {
                        top.linkTo(parent.top, BIG_MARGIN)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
            )


            Icon(
                painter = painterResource(id = R.drawable.ic_outline_location),
                contentDescription = "",
                tint = MaterialTheme.colors.primary,
                modifier = Modifier
                    .constrainAs(icLocation) {
                        top.linkTo(txtLocation.top)
                        bottom.linkTo(txtLocation.bottom)
                        end.linkTo(txtLocation.start, SMALL_MARGIN)
                    }
            )


            Column(
                modifier = Modifier
                    .constrainAs(coContainer) {
                        top.linkTo(icLocation.bottom, 50.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                    .fillMaxWidth()
                    .padding(start = LARGE_MARGIN, end = LARGE_MARGIN)
                    .clip(RoundedCornerShape(20.dp))
                    .background(MaterialTheme.colors.onBackground),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = "${data?.main?.temp.toString().substring(0, lastIndex)}$unitLetter",
                    fontSize = 90.sp,
                    fontFamily = FontFamily.Serif,
                    color = MaterialTheme.colors.primary
                )

                Text(
                    text = "${stringResource(id = R.string.feels_like)} ${
                        data?.main?.feels_like.toString().substring(0, lastIndex)
                    }$unitLetter",
                    fontSize = 16.sp,
                    color = MaterialTheme.colors.primaryVariant,
                    modifier = Modifier.padding(top = SMALL_MARGIN)
                )


                Text(
                    text = data?.weather?.get(0)?.description.toString(),
                    fontSize = 18.sp,
                    color = MaterialTheme.colors.secondary,
                    modifier = Modifier.padding(top = VERY_SMALL_MARGIN)
                )


                Image(
                    painter = rememberImagePainter(
                        data = "$IMAGE_URL${data?.weather?.get(0)?.icon}$SIZE"
                    ),
                    contentDescription = "",
                    modifier = Modifier.size(50.dp)
                )
            }


            Icon(
                painter = painterResource(id = R.drawable.ic_outline_wind),
                contentDescription = "",
                tint = MaterialTheme.colors.secondary,
                modifier = Modifier
                    .constrainAs(icWind) {
                        top.linkTo(coContainer.bottom, LARGE_MARGIN)
                        start.linkTo(parent.start, LARGE_MARGIN)
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


            Button(
                onClick = {
                    navController.navigate(
                        "$DETAILS_SCREEN/${longitude.toFloat()}/${latitude.toFloat()}/${data?.clouds?.all}"
                    )
                },
                elevation = null,
                colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.background),
                modifier = Modifier.constrainAs(btnMore) {
                    top.linkTo(txtWind.top)
                    bottom.linkTo(txtWind.bottom)
                    end.linkTo(parent.end, SMALL_MARGIN)
                }) {

                Text(
                    text = stringResource(R.string.more),
                    textDecoration = TextDecoration.Underline,
                    color = MaterialTheme.colors.primaryVariant,
                )

                Image(
                    painter = painterResource(id = R.drawable.ic_keyboard_arrow_right),
                    contentDescription = "",
                    alpha = 0.7f
                )
            }



            Icon(
                painter = painterResource(id = R.drawable.ic_outline_visibility),
                contentDescription = "",
                tint = MaterialTheme.colors.secondary,
                modifier = Modifier
                    .constrainAs(icVisibility) {
                        top.linkTo(icWind.bottom, MEDIUM_MARGIN)
                        start.linkTo(parent.start, LARGE_MARGIN)
                    }
            )


            Text(
                text = "${stringResource(id = R.string.visibility)} ${data?.visibility?.div(1000)} ${
                    stringResource(id = R.string.km)
                }",
                fontSize = 16.sp,
                color = MaterialTheme.colors.primaryVariant,
                modifier = Modifier
                    .constrainAs(txtVisibility) {
                        top.linkTo(icVisibility.top)
                        start.linkTo(icVisibility.end, SMALL_MARGIN)
                    }
            )


            Circle(
                modifier = Modifier
                    .constrainAs(boxHumidityPercentage) {
                        top.linkTo(txtVisibility.bottom, MEDIUM_MARGIN)
                        bottom.linkTo(txtDate.top, MEDIUM_MARGIN)
                        start.linkTo(txtVisibility.end)
                        end.linkTo(parent.end)
                    },

                if (data?.main?.humidity != null) {
                    data?.main?.humidity?.toDouble()?.div(100)!!.toFloat()
                } else {
                    0f
                }, MaterialTheme.colors.secondary
            )

            Text(
                text = stringResource(R.string.humidity),
                color = MaterialTheme.colors.primaryVariant,
                fontSize = 16.sp,
                modifier = Modifier.constrainAs(txtHumidity) {
                    bottom.linkTo(boxHumidityPercentage.bottom)
                    top.linkTo(boxHumidityPercentage.top)
                    end.linkTo(boxHumidityPercentage.start, MEDIUM_MARGIN)
                }
            )


            Text(
                buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            color = MaterialTheme.colors.primary
                        )
                    ) {
                        append("${stringResource(id = R.string.today)}\n")
                    }
                    withStyle(
                        style = SpanStyle(
                            color = MaterialTheme.colors.primaryVariant,
                            fontSize = 14.sp
                        )
                    ) {
                        append(formatDate(FORMAT_TYPE))
                    }
                },
                modifier = Modifier
                    .constrainAs(txtDate) {
                        start.linkTo(parent.start, LARGE_MARGIN)
                        top.linkTo(boxHumidityPercentage.bottom)
                    }
            )


            LazyRow(
                modifier = Modifier
                    .constrainAs(lrWeather) {
                        top.linkTo(txtDate.bottom, MEDIUM_MARGIN)
                        end.linkTo(parent.end)
                        start.linkTo(parent.start)
                    }
                    .padding(start = MEDIUM_MARGIN, end = MEDIUM_MARGIN)
            ) {
                items(forecast) {
                    ListTodayWeather(forecast = it)
                }
            }
        }
    }
}