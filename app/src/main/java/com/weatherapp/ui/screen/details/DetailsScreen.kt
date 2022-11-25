package com.weatherapp.ui.screen.details

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.rememberImagePainter
import com.weatherapp.R
import com.weatherapp.data.local.Constants.CLOUD
import com.weatherapp.data.local.Constants.FORMAT_TYPE
import com.weatherapp.data.local.Constants.IMAGE_URL
import com.weatherapp.data.local.Constants.SIZE
import com.weatherapp.data.model.AirQuality
import com.weatherapp.data.model.forecast.FiveDaysForecastResponse
import com.weatherapp.data.model.forecast.ListItem
import com.weatherapp.data.model.pollution.AirPollutionResponse
import com.weatherapp.data.network.Resource
import com.weatherapp.data.viewmodel.DetailsViewModel
import com.weatherapp.ui.screen.search.ListWeatherForecast
import com.weatherapp.ui.theme.BIG_MARGIN
import com.weatherapp.ui.theme.LARGE_MARGIN
import com.weatherapp.ui.theme.MEDIUM_MARGIN
import com.weatherapp.ui.theme.SMALL_MARGIN
import com.weatherapp.util.*

@Composable
fun DetailsScreen(
    viewModel: DetailsViewModel,
    longitude: Float?,
    latitude: Float?,
    cloudiness: Int?
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
                UpdateUi(viewModel, apiError, cloudiness)
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

    if (latitude != null && longitude != null) {
        LaunchedEffect(key1 = true) {
            viewModel.initGetDetails(
                latitude.toDouble(),
                longitude.toDouble()
            )
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


@Composable
fun UpdateUi(
    viewModel: DetailsViewModel,
    apiError: MutableState<String>,
    cloudiness: Int?
) {
    val context = LocalContext.current
    var fiveDaysForecast: List<ListItem> = listOf()
    val forecastResponse by viewModel.weatherForecast.collectAsState()


    if (forecastResponse is Resource.Success) {
        fiveDaysForecast =
            (forecastResponse as Resource.Success<FiveDaysForecastResponse>).value.list!!
    } else if (forecastResponse is Resource.Failure) {
        LaunchedEffect(key1 = true) {
            context.handleApiError(forecastResponse as Resource.Failure)
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .padding(bottom = SMALL_MARGIN)
    ) {
        item {
            Header(viewModel, apiError, cloudiness)
        }
        items(fiveDaysForecast) {
            ListWeatherForecast(
                forecast = it,
                timeVisibility = false
            )
        }
    }
}


@Composable
fun Header(
    viewModel: DetailsViewModel,
    apiError: MutableState<String>,
    cloudiness: Int?
) {
    ConstraintLayout {

        val (txtTitle, divider, txtToday, circleAQI, txtDescriptionAQI,
            txtMoreDes, txtTitlePollutants, txtTitleFiveDays, secondDivider, circleCloudiness,
            txtCloudinessTitle, dividerCloudiness, iconCloud) = createRefs()

        val context = LocalContext.current
        var airPollution: AirPollutionResponse? = null
        var airQuality: AirQuality? = null
        val pollutionResponse by viewModel.currentPollution.collectAsState()


        if (pollutionResponse is Resource.Success) {
            airPollution = (pollutionResponse as Resource.Success<AirPollutionResponse>).value
            val aqi = airPollution.list?.get(0)?.main?.aqi
            if (aqi != null) {
                airQuality = viewModel.getAirQuality(aqi)
            }

        } else if (pollutionResponse is Resource.Failure) {
            context.handleApiError(pollutionResponse as Resource.Failure)
            apiError.value = context.getString(R.string.connection_error)
            viewModel.requestState.value = RequestState.ERROR
        }


        Text(
            modifier = Modifier.constrainAs(txtTitle) {
                top.linkTo(parent.top, LARGE_MARGIN)
                start.linkTo(parent.start, LARGE_MARGIN)
            },
            text = stringResource(R.string.current_air),
            color = MaterialTheme.colors.primary,
            fontSize = 18.sp
        )

        Line(modifier = Modifier
            .constrainAs(divider) {
                top.linkTo(txtTitle.bottom, SMALL_MARGIN)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
            .padding(start = LARGE_MARGIN, end = LARGE_MARGIN), thickness = 1.dp
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
                    append(convertUnixDate(airPollution?.list?.get(0)?.dt ?: 0, FORMAT_TYPE))
                }
            },
            modifier = Modifier
                .constrainAs(txtToday) {
                    start.linkTo(parent.start, LARGE_MARGIN)
                    top.linkTo(divider.bottom, MEDIUM_MARGIN)
                }
        )

        if (airQuality != null) {
            CircleCustom(
                modifier = Modifier.constrainAs(circleAQI) {
                    top.linkTo(txtToday.bottom, MEDIUM_MARGIN)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
                percentage = airQuality.percentage,
                colorAirQuality = airQuality.color
            )

            Text(
                modifier = Modifier.constrainAs(txtDescriptionAQI) {
                    top.linkTo(circleAQI.bottom, MEDIUM_MARGIN)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }, text = airQuality.description,
                color = MaterialTheme.colors.primary,
                fontSize = 18.sp
            )
        }


        Text(
            text = stringResource(R.string.air_quailty_index_des),
            textAlign = TextAlign.Center,
            modifier = Modifier
                .constrainAs(txtMoreDes) {
                    top.linkTo(txtDescriptionAQI.bottom, MEDIUM_MARGIN)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .padding(start = BIG_MARGIN, end = BIG_MARGIN),
            color = MaterialTheme.colors.primaryVariant,
            fontSize = 14.sp
        )

        CurrentPollutants(
            modifier = Modifier
                .constrainAs(txtTitlePollutants) {
                    top.linkTo(txtMoreDes.bottom, BIG_MARGIN)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .padding(start = LARGE_MARGIN, end = LARGE_MARGIN),
            viewModel,
            airPollution
        )


        // Current cloudiness title
        Text(
            modifier = Modifier.constrainAs(txtCloudinessTitle) {
                top.linkTo(txtTitlePollutants.bottom, BIG_MARGIN)
                start.linkTo(parent.start, LARGE_MARGIN)
            },
            text = stringResource(R.string.current_cloudiness),
            fontSize = 18.sp,
            color = MaterialTheme.colors.primary
        )

        Image(
            modifier = Modifier
                .constrainAs(iconCloud) {
                    top.linkTo(txtCloudinessTitle.top)
                    bottom.linkTo(txtCloudinessTitle.bottom)
                    start.linkTo(txtCloudinessTitle.end, SMALL_MARGIN)
                }
                .size(40.dp),
            contentDescription = "",
            painter = rememberImagePainter(
                data = "$IMAGE_URL$CLOUD$SIZE"
            )
        )

        // Line
        Line(modifier = Modifier
            .constrainAs(dividerCloudiness) {
                top.linkTo(txtCloudinessTitle.bottom, SMALL_MARGIN)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
            .padding(start = LARGE_MARGIN, end = LARGE_MARGIN), thickness = 1.dp
        )


        // Cloudiness in percent
        Circle(
            modifier = Modifier.constrainAs(circleCloudiness) {
                top.linkTo(dividerCloudiness.bottom, LARGE_MARGIN)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            },
            percentage = cloudiness?.toDouble()?.div(100)?.toFloat() ?: 0f,
            arcColor = MaterialTheme.colors.secondary
        )


        // Current 3 days forecast
        Text(
            modifier = Modifier.constrainAs(txtTitleFiveDays) {
                top.linkTo(circleCloudiness.bottom, BIG_MARGIN)
                start.linkTo(parent.start, LARGE_MARGIN)
            },
            text = stringResource(R.string.title_five_days),
            color = MaterialTheme.colors.primary,
            fontSize = 18.sp
        )

        Line(modifier = Modifier
            .constrainAs(secondDivider) {
                top.linkTo(txtTitleFiveDays.bottom, SMALL_MARGIN)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
            .padding(start = LARGE_MARGIN, end = LARGE_MARGIN), thickness = 1.dp
        )
    }
}


@Composable
fun CircleCustom(
    modifier: Modifier,
    percentage: Float,
    colorAirQuality: Color
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
                color = colorAirQuality,
                90f,
                360 * currentPercentage.value,
                useCenter = false,
                style = Stroke(5.dp.toPx(), cap = StrokeCap.Round)
            )
        }

        Text(
            buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        color = MaterialTheme.colors.primary,
                        fontSize = 25.sp
                    )
                ) {
                    append("${(currentPercentage.value * 5).toInt()}\n")
                }
                withStyle(
                    style = SpanStyle(
                        color = MaterialTheme.colors.primaryVariant,
                        fontSize = 13.sp
                    )
                ) {
                    append(stringResource(id = R.string.aqi))
                }
            }
        )
    }
}