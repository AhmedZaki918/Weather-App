package com.weatherapp.ui.screen.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.rememberImagePainter
import com.weatherapp.data.model.forecast.ListItem
import com.weatherapp.ui.theme.*
import com.weatherapp.util.Line


@Composable
fun ListWeatherForecast(forecast: ListItem) {


    ConstraintLayout(modifier = Modifier.fillMaxWidth()) {
        val (txtDateTime, imageWeather, txtWeather, txtTemp, line) = createRefs()
        val lastIndex = forecast.main?.temp.toString().indexOf(".")


        Text(
            buildAnnotatedString {
                withStyle(style = SpanStyle(color = Secondary, fontSize = 14.sp)) {
                    append("${forecast.dt_txt?.substring(0, 10)}\n")
                }
                withStyle(style = SpanStyle(color = Hint, fontSize = 16.sp)) {
                    append(forecast.dt_txt?.substring(11, 16).toString())
                }
            },
            modifier = Modifier
                .constrainAs(txtDateTime) {
                    start.linkTo(parent.start, LARGE_MARGIN)
                    top.linkTo(parent.top, CUSTOM_MARGIN)
                }
        )

        Image(painter = rememberImagePainter(
            data = "http://openweathermap.org/img/wn/${
                forecast.weather?.get(0)?.icon
            }@2x.png"
        ),
            contentDescription = "",
            modifier = Modifier
                .constrainAs(imageWeather) {
                    start.linkTo(txtDateTime.end, LARGE_MARGIN)
                    top.linkTo(txtDateTime.top)
                    bottom.linkTo(txtDateTime.bottom)
                }
                .size(50.dp)
        )

        Text(
            text = forecast.weather?.get(0)?.main.toString(),
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
            text = forecast.main?.temp.toString().substring(0, lastIndex) + "°",
            fontSize = 20.sp,
            color = Secondary,
            modifier = Modifier
                .constrainAs(txtTemp) {
                    top.linkTo(txtWeather.top)
                    end.linkTo(parent.end, BIG_MARGIN)
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

