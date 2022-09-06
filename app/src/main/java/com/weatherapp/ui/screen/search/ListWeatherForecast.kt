package com.weatherapp.ui.screen.search

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.weatherapp.R
import com.weatherapp.data.model.FiveDaysDummy
import com.weatherapp.ui.theme.*


@Composable
fun ListWeatherForecast(forecast: FiveDaysDummy) {

    ConstraintLayout(
        modifier = Modifier.fillMaxWidth()
    ) {
        val (txtDateTime, imageWeather, txtWeather, txtTemp, line) = createRefs()


        Text(
            buildAnnotatedString {
                withStyle(style = SpanStyle(color = Secondary, fontSize = 14.sp)) {
                    append("${forecast.date}\n")
                }
                withStyle(style = SpanStyle(color = Hint, fontSize = 16.sp)) {
                    append(forecast.time.toString())
                }
            },
            modifier = Modifier
                .constrainAs(txtDateTime) {
                    start.linkTo(parent.start, LARGE_MARGIN)
                    top.linkTo(parent.top, CUSTOM_MARGIN)
                }
        )

        Icon(painter = painterResource(id = forecast.icon!!),
            contentDescription = "",
            tint = Color.White,
            modifier = Modifier
                .constrainAs(imageWeather) {
                    start.linkTo(txtDateTime.end, LARGE_MARGIN)
                    top.linkTo(txtDateTime.top)
                    bottom.linkTo(txtDateTime.bottom)
                }
                .size(50.dp)
        )

        Text(
            text = forecast.weather.toString(),
            fontSize = 16.sp,
            color = Secondary,
            modifier = Modifier
                .constrainAs(txtWeather) {
                    start.linkTo(imageWeather.end, SMALL_MARGIN)
                    top.linkTo(imageWeather.top)
                    bottom.linkTo(imageWeather.bottom)
                }
        )


        Text(
            text = forecast.temp.toString(),
            fontSize = 20.sp,
            color = Secondary,
            modifier = Modifier
                .constrainAs(txtTemp) {
                    top.linkTo(txtWeather.top)
                    end.linkTo(parent.end, BIG_MARGIN)
                }
        )

        Divider(
            color = Color.LightGray.copy(alpha = 0.1f), thickness = 1.dp,
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


@Preview
@Composable
fun ListWeatherForecastPreview() {
    ListWeatherForecast(
        FiveDaysDummy("2022-09-04", "12:00", R.drawable.preview_cloudy, "Clear", "30°"),
    )
}