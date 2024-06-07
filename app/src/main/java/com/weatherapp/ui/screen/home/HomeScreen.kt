package com.weatherapp.ui.screen.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.weatherapp.R
import com.weatherapp.data.local.Constants
import com.weatherapp.data.local.Constants.C_UNIT
import com.weatherapp.data.local.Constants.DETAILS_SCREEN
import com.weatherapp.data.local.Constants.FORMAT_TYPE
import com.weatherapp.data.local.Constants.F_UNIT
import com.weatherapp.data.local.Constants.IMAGE_URL
import com.weatherapp.data.local.Constants.METRIC
import com.weatherapp.data.local.Constants.SIZE
import com.weatherapp.data.local.Constants.TWELVE_PM
import com.weatherapp.data.model.weather.CurrentWeatherResponse
import com.weatherapp.data.viewmodel.HomeViewModel
import com.weatherapp.ui.screen.search.ListWeatherForecast
import com.weatherapp.ui.theme.BIG_MARGIN
import com.weatherapp.ui.theme.CUSTOM_MARGIN
import com.weatherapp.ui.theme.LARGE_MARGIN
import com.weatherapp.ui.theme.MEDIUM_MARGIN
import com.weatherapp.ui.theme.SMALL_MARGIN
import com.weatherapp.ui.theme.VERY_SMALL_MARGIN
import com.weatherapp.util.Circle
import com.weatherapp.util.RequestState
import com.weatherapp.util.formatDate
import com.weatherapp.util.shimmerEffect

@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    navController: NavHostController
) {
    val uiState = viewModel.uiState.collectAsState()
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 80.dp)
    ) {


        // Current weather
        if (uiState.value.currentWeatherState == RequestState.ERROR) {
            item { ErrorUi() }
        } else {
            when (uiState.value.currentWeatherState) {
                RequestState.IDLE -> {
                    item {
                        LoadingScreen()
                    }
                }

                RequestState.SUCCESS -> {
                    item {
                        CurrentWeather(
                            uiState.value.currentWeather,
                            uiState,
                            navController
                        )
                    }
                }

                else -> Unit
            }


             // Forecast
            when (uiState.value.forecastState) {
                RequestState.SUCCESS -> {
                    this@LazyColumn.forecast(uiState)
                }

                RequestState.ERROR -> {
                    item { ErrorUi() }
                }

                else -> Unit
            }
        }
    }
}


@Composable
fun LoadingScreen() {

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
    ) {


        val (cityBox, tempBox, feelsLikeBox, weatherDesBox, iconBox, windSpeedBox,
            visibilityBox, moreBox, weatherRow
        ) = createRefs()

        Box(
            modifier = Modifier
                .constrainAs(cityBox) {
                    top.linkTo(parent.top, BIG_MARGIN)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .width(100.dp)
                .height(80.dp)
                .padding(start = SMALL_MARGIN, end = SMALL_MARGIN, bottom = BIG_MARGIN)
                .clip(shape = RoundedCornerShape(10.dp))
                .shimmerEffect()
        )


        Box(
            modifier = Modifier
                .constrainAs(tempBox) {
                    top.linkTo(cityBox.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .width(220.dp)
                .height(120.dp)
                .padding(start = SMALL_MARGIN, end = SMALL_MARGIN, bottom = CUSTOM_MARGIN)
                .clip(shape = RoundedCornerShape(10.dp))
                .shimmerEffect()
        )


        Box(
            modifier = Modifier
                .constrainAs(feelsLikeBox) {
                    top.linkTo(tempBox.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .width(130.dp)
                .height(20.dp)
                .padding(start = SMALL_MARGIN, end = SMALL_MARGIN, bottom = SMALL_MARGIN)
                .clip(shape = RoundedCornerShape(10.dp))
                .shimmerEffect()
        )


        Box(
            modifier = Modifier
                .constrainAs(weatherDesBox) {
                    top.linkTo(feelsLikeBox.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .width(100.dp)
                .height(20.dp)
                .padding(start = SMALL_MARGIN, end = SMALL_MARGIN, bottom = SMALL_MARGIN)
                .clip(shape = RoundedCornerShape(10.dp))
                .shimmerEffect()
        )



        Box(
            modifier = Modifier
                .constrainAs(iconBox) {
                    top.linkTo(weatherDesBox.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .width(40.dp)
                .height(30.dp)
                .padding(start = SMALL_MARGIN, end = SMALL_MARGIN, bottom = MEDIUM_MARGIN)
                .clip(shape = RoundedCornerShape(10.dp))
                .shimmerEffect()
        )


        Box(
            modifier = Modifier
                .constrainAs(windSpeedBox) {
                    top.linkTo(iconBox.bottom, LARGE_MARGIN)
                    start.linkTo(parent.start)
                }
                .width(130.dp)
                .height(30.dp)
                .padding(start = LARGE_MARGIN, bottom = MEDIUM_MARGIN)
                .clip(shape = RoundedCornerShape(10.dp))
                .shimmerEffect()
        )


        Box(
            modifier = Modifier
                .constrainAs(moreBox) {
                    top.linkTo(iconBox.bottom, LARGE_MARGIN)
                    end.linkTo(parent.end)
                }
                .width(80.dp)
                .height(30.dp)
                .padding(end = LARGE_MARGIN)
                .clip(shape = RoundedCornerShape(10.dp))
                .shimmerEffect()
        )


        Box(
            modifier = Modifier
                .constrainAs(visibilityBox) {
                    top.linkTo(windSpeedBox.bottom)
                    start.linkTo(parent.start)
                }
                .width(130.dp)
                .height(30.dp)
                .padding(start = LARGE_MARGIN)
                .clip(shape = RoundedCornerShape(10.dp))
                .shimmerEffect()
        )


        Box(
            modifier = Modifier
                .constrainAs(weatherRow) {
                    top.linkTo(visibilityBox.bottom, MEDIUM_MARGIN)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .fillMaxWidth()
                .height(150.dp)
                .padding(start = LARGE_MARGIN, end = LARGE_MARGIN)
                .clip(shape = RoundedCornerShape(10.dp))
                .shimmerEffect()
        )

//
//        Row(modifier = Modifier
//            .constrainAs(weatherRow) {
//                top.linkTo(visibilityBox.bottom, MEDIUM_MARGIN)
//                start.linkTo(parent.start)
//                end.linkTo(parent.end)
//            }
//            .fillMaxWidth()
//            .wrapContentHeight()
//            .padding(start = SMALL_MARGIN)) {
//
//            for (x in 0..4) {
//                Card(
//                    shape = RoundedCornerShape(10.dp),
//                    elevation = 10.dp,
//                    modifier = Modifier
//                        .padding(8.dp)
//                        .size(60.dp, 110.dp)
//                        .shimmerEffect()
//                ) {
//
//                }
//            }
//        }
    }
}


fun LazyListScope.forecast(uiState: State<HomeUiState>) {

    // Today weather every 3 hours
    item {
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = MEDIUM_MARGIN, end = MEDIUM_MARGIN)
        ) {
            itemsIndexed(uiState.value.fiveDaysForecast.list!!) { index, item ->
                if (index <= 8) {
                    ListTodayWeather(forecast = item)
                }
            }
        }
    }


    // Forecast in next 5 days
    var savedDate: String? = null
    val savedTime = TWELVE_PM
    val forecast = uiState.value.fiveDaysForecast.list


    itemsIndexed(forecast!!) { index, item ->
        // Save date and time (first time only)
        if (index == 0) {
            savedDate = forecast[index].dt_txt
        }
        // Extract date and time to use it in comparison
        val currentDate = forecast[index].dt_txt?.substring(0, 10)
        val currentTime = forecast[index].dt_txt?.substring(11, 16)

        // Display the next 5 days forecast on 3:00 pm only
        if (currentDate != savedDate && currentTime == savedTime) {
            savedDate = currentDate
            ListWeatherForecast(
                forecast = item,
                timeVisibility = true
            )
        }
    }

}


@Composable
fun CurrentWeather(
    weatherResponse: CurrentWeatherResponse,
    uiState: State<HomeUiState>,
    navController: NavHostController
) {

    ConstraintLayout(
        modifier = Modifier
            .background(MaterialTheme.colors.background)
            .fillMaxSize()
            .padding(bottom = MEDIUM_MARGIN)
    ) {

        val (
            icLocation, txtLocation,
            icWind, txtWind, txtVisibility, icVisibility,
            boxHumidityPercentage, txtHumidity, txtDate, coContainer, btnMore
        ) = createRefs()


        val windSpeed = weatherResponse.wind?.speed?.times(60)?.times(60)?.div(1000)?.toInt()
        val unitLetter = if (uiState.value.tempUnit == METRIC) C_UNIT else F_UNIT
        val lastIndex = weatherResponse.main?.temp.toString().indexOf(".")

        // Current location
        Text(
            text = weatherResponse.name.toString(),
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

            // Temperature
            Text(
                text = "${
                    weatherResponse.main?.temp.toString().substring(0, lastIndex)
                }$unitLetter",
                fontSize = 90.sp,
                fontFamily = FontFamily.Serif,
                color = MaterialTheme.colors.primary
            )

            // Feels like
            Text(
                text = "${stringResource(id = R.string.feels_like)} ${
                    weatherResponse.main?.feels_like.toString().substring(0, lastIndex)
                }$unitLetter",
                fontSize = 16.sp,
                color = MaterialTheme.colors.primaryVariant,
                modifier = Modifier.padding(top = SMALL_MARGIN)
            )

            // Weather description
            Text(
                text = weatherResponse.weather?.get(0)?.description.toString(),
                fontSize = 18.sp,
                color = MaterialTheme.colors.secondary,
                modifier = Modifier.padding(top = VERY_SMALL_MARGIN)
            )

            Image(
                painter = rememberImagePainter(
                    data = "$IMAGE_URL${weatherResponse.weather?.get(0)?.icon}$SIZE"
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

        // Wind speed
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

        // More
        Button(
            onClick = {
                val latitude = uiState.value.geocoding[0].lat
                val longitude = uiState.value.geocoding[0].lon

                navController.navigate(
                    "$DETAILS_SCREEN/${longitude?.toFloat()}/${latitude?.toFloat()}/${weatherResponse.clouds?.all}"
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

        // Visibility
        Text(
            text = "${stringResource(id = R.string.visibility)} ${
                weatherResponse.visibility?.div(
                    1000
                )
            } ${
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

        // Humidity circle
        Circle(
            modifier = Modifier
                .constrainAs(boxHumidityPercentage) {
                    top.linkTo(txtVisibility.bottom, MEDIUM_MARGIN)
                    bottom.linkTo(txtDate.top, MEDIUM_MARGIN)
                    start.linkTo(txtVisibility.end)
                    end.linkTo(parent.end)
                },

            if (weatherResponse.main?.humidity != null) {
                weatherResponse.main.humidity.toDouble().div(100).toFloat()
            } else {
                0f
            }, MaterialTheme.colors.secondary
        )

        // Humidity title
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


        // Today date
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
    }
}

@Composable
fun ErrorUi() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 300.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        Text(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = MEDIUM_MARGIN),
            text = "Something went wrong..",
            textAlign = TextAlign.Center,
            fontSize = 16.sp,
            color = MaterialTheme.colors.primaryVariant
        )
    }
}