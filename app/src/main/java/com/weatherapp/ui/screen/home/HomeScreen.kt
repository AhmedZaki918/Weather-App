package com.weatherapp.ui.screen.home

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.weatherapp.R
import com.weatherapp.ui.theme.*

@Composable
fun HomeScreen() {

    var percentage = 0.60f

    ConstraintLayout(
        modifier = Modifier
            .background(HomeBackground)
            .fillMaxSize()
    ) {

        val (icLocation, txtLocation, txtTemperature, txtFeelsLike, txtWeather,
            icWind, txtWind, ivWeather, txtVisibility, icVisibility,
            boxHumidityPercentage, txtHumidity) = createRefs()

        Text(
            text = "Cairo",
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
                    end.linkTo(txtLocation.start, MEDIUM_MARGIN)
                }
        )

        Text(
            text = "31°C",
            fontSize = 80.sp,
            color = Color.White,
            modifier = Modifier
                .constrainAs(txtTemperature) {
                    top.linkTo(txtLocation.bottom, 50.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        )

        Text(
            text = "Feels like 28°C",
            fontSize = 18.sp,
            color = Secondary,
            modifier = Modifier
                .constrainAs(txtFeelsLike) {
                    top.linkTo(txtTemperature.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        )

        Text(text = "Cloudy",
            fontSize = 20.sp,
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
                    start.linkTo(parent.start, BIG_MARGIN)
                }
        )

        Text(text = "5.4 m/s",
            fontSize = 16.sp,
            color = Secondary,
            modifier = Modifier
                .constrainAs(txtWind) {
                    top.linkTo(icWind.top)
                    start.linkTo(icWind.end, MEDIUM_MARGIN)
                }
        )


        Image(
            painter = painterResource(id = R.drawable.preview_cloudy),
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
                    top.linkTo(icWind.bottom, SMALL_MARGIN)
                    start.linkTo(parent.start, BIG_MARGIN)
                }
        )

        Text(
            text = "Visibility 10 km",
            fontSize = 16.sp,
            color = Secondary,
            modifier = Modifier
                .constrainAs(txtVisibility) {
                    top.linkTo(icVisibility.top)
                    start.linkTo(icVisibility.end, MEDIUM_MARGIN)
                }
        )


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
            modifier = Modifier
                .constrainAs(boxHumidityPercentage) {
                    top.linkTo(icVisibility.bottom, BIG_MARGIN)
                    start.linkTo(parent.start, BIG_MARGIN)
                }
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


        Text(
            text = "Humidity",
            color = Secondary,
            fontSize = 18.sp,
            modifier = Modifier.constrainAs(txtHumidity) {
                start.linkTo(boxHumidityPercentage.end, LARGE_MARGIN)
                top.linkTo(boxHumidityPercentage.top)
                bottom.linkTo(boxHumidityPercentage.bottom)
            }
        )
    }
}


@Composable
@Preview(showSystemUi = true)
fun HomeScreenPreview() {
    HomeScreen()
}