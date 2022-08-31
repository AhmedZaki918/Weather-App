package com.weatherapp.ui.screen.home

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.weatherapp.R
import com.weatherapp.RequestState
import com.weatherapp.data.local.Constants.FORMAT_TYPE
import com.weatherapp.data.model.forecast.FiveDaysForecastResponse
import com.weatherapp.data.model.forecast.ListItem
import com.weatherapp.data.model.weather.CurrentWeatherResponse
import com.weatherapp.data.network.Resource
import com.weatherapp.data.viewmodel.HomeViewModel
import com.weatherapp.formatDate
import com.weatherapp.handleApiError
import com.weatherapp.ui.theme.*

@Composable
fun HomeScreen(
    viewModel: HomeViewModel
) {

    if (viewModel.requestState.value == RequestState.LOADING) {
        LoadingScreen()
    } else {
        UpdateUi(viewModel)
    }
}


@Composable
fun LoadingScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(HomeBackground),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}


@ExperimentalCoilApi
@Composable
fun UpdateUi(
    viewModel: HomeViewModel
) {

    val context = LocalContext.current
    val scrollState = rememberScrollState()
    var data: CurrentWeatherResponse? = null
    var forecast: List<ListItem> = listOf()

    var windSpeed: Int? = null

    ConstraintLayout(
        modifier = Modifier
            .background(HomeBackground)
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(bottom = 70.dp)
    ) {

        val (
            icLocation, txtLocation, txtTemperature, txtFeelsLike, txtWeather,
            icWind, txtWind, ivWeather, txtVisibility, icVisibility,
            boxHumidityPercentage, txtHumidity, txtDate, lrWeather
        ) = createRefs()


        val weatherResponse by viewModel.currentWeather.collectAsState()
        if (weatherResponse is Resource.Success) {
            data = (weatherResponse as Resource.Success<CurrentWeatherResponse>).value
            windSpeed = data?.wind?.speed?.times(60)?.times(60)?.div(1000)?.toInt()

        } else if (weatherResponse is Resource.Failure) {
            context.handleApiError(weatherResponse as Resource.Failure)
        }

        val forecastResponse by viewModel.weatherForecast.collectAsState()
        if (forecastResponse is Resource.Success) {
            forecast = (forecastResponse as Resource.Success<FiveDaysForecastResponse>).value.list!!

        } else if (forecastResponse is Resource.Failure) {
            context.handleApiError(forecastResponse as Resource.Failure)
        }


        Text(
            text = data?.name.toString(),
            color = Color.White,
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
            tint = Color.White,
            modifier = Modifier
                .constrainAs(icLocation) {
                    top.linkTo(txtLocation.top)
                    bottom.linkTo(txtLocation.bottom)
                    end.linkTo(txtLocation.start, SMALL_MARGIN)
                }
        )


        Text(
            text = "${data?.main?.temp.toString().substring(0, 2)}°C",
            fontSize = 90.sp,
            fontFamily = FontFamily.Serif,
            color = Color.White,
            modifier = Modifier
                .constrainAs(txtTemperature) {
                    top.linkTo(txtLocation.bottom, 50.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        )

        Text(
            text = "${stringResource(id = R.string.feels_like)} ${
                data?.main?.feels_like.toString().substring(0, 2)
            }°C",
            fontSize = 16.sp,
            color = Secondary,
            modifier = Modifier
                .constrainAs(txtFeelsLike) {
                    top.linkTo(txtTemperature.bottom, SMALL_MARGIN)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        )


        Text(
            text = data?.weather?.get(0)?.main.toString(),
            fontSize = 18.sp,
            color = Hint,
            modifier = Modifier
                .constrainAs(txtWeather) {
                    top.linkTo(txtFeelsLike.bottom, VERY_SMALL_MARGIN)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        )

        Icon(
            painter = painterResource(id = R.drawable.ic_outline_wind),
            contentDescription = "",
            tint = Hint,
            modifier = Modifier
                .constrainAs(icWind) {
                    top.linkTo(ivWeather.bottom, LARGE_MARGIN)
                    start.linkTo(parent.start, LARGE_MARGIN)
                }
        )

        Text(
            text = "$windSpeed ${stringResource(id = R.string.km_h)}",
            fontSize = 16.sp,
            color = Secondary,
            modifier = Modifier
                .constrainAs(txtWind) {
                    top.linkTo(icWind.top)
                    start.linkTo(icWind.end, SMALL_MARGIN)
                }
        )

        Image(
            painter = rememberImagePainter(
                data = "http://openweathermap.org/img/wn/${
                    data?.weather?.get(
                        0
                    )?.icon
                }@2x.png"
            ),
            contentDescription = "",
            modifier = Modifier
                .constrainAs(ivWeather) {
                    top.linkTo(txtWeather.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .size(50.dp)
        )

        Icon(
            painter = painterResource(id = R.drawable.ic_outline_visibility),
            contentDescription = "",
            tint = Hint,
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
            color = Secondary,
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
            }
        )

        Text(
            text = stringResource(R.string.humidity),
            color = Secondary,
            fontSize = 16.sp,
            modifier = Modifier.constrainAs(txtHumidity) {
                bottom.linkTo(boxHumidityPercentage.bottom)
                top.linkTo(boxHumidityPercentage.top)
                end.linkTo(boxHumidityPercentage.start, MEDIUM_MARGIN)
            }
        )


        Text(
            buildAnnotatedString {
                withStyle(style = SpanStyle(color = Color.White)) {
                    append("${stringResource(id = R.string.today)}\n")
                }
                withStyle(style = SpanStyle(color = Secondary, fontSize = 14.sp)) {
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


@Composable
fun Circle(
    modifier: Modifier,
    percentage: Float
) {
    var animation by remember {
        mutableStateOf(false)
    }

    val currentPercentage = animateFloatAsState(
        targetValue = if (animation) percentage else 0f,
        animationSpec = tween(
            durationMillis = 1000,
            delayMillis = 0
        )
    )

    LaunchedEffect(key1 = true) {
        animation = true
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .size(50.dp * 2f)
    ) {
        Canvas(
            modifier = Modifier
                .size(50.dp * 2f)
        ) {
            drawArc(
                color = Hint,
                -90f,
                360 * currentPercentage.value,
                useCenter = false,
                style = Stroke(4.dp.toPx(), cap = StrokeCap.Round)
            )
        }
        Text(
            text = (currentPercentage.value * 100).toInt().toString() + "%",
            color = Color.White,
            fontSize = 20.sp,
        )
    }
}


@Composable
@Preview(showSystemUi = true)
fun HomeScreenPreview() {
}