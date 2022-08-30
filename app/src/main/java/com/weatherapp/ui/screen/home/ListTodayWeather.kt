package com.weatherapp.ui.screen.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.weatherapp.data.model.forecast.ListItem
import com.weatherapp.ui.theme.ListContent
import com.weatherapp.ui.theme.SMALL_MARGIN
import com.weatherapp.ui.theme.Secondary

@ExperimentalCoilApi
@Composable
fun ListTodayWeather(forecast: ListItem) {


    Card(
        shape = RoundedCornerShape(30.dp),
        elevation = 10.dp,
        modifier = Modifier
            .padding(8.dp)
            .size(60.dp, 110.dp),
        backgroundColor = ListContent
    ) {

        ConstraintLayout {
            val (icWeather, txtTemp, txtTime) = createRefs()


            Image(
                painter = rememberImagePainter(
                    data = "http://openweathermap.org/img/wn/${
                        forecast.weather?.get(0)?.icon
                    }@2x.png"
                ),
                contentDescription = "",
                modifier = Modifier
                    .constrainAs(icWeather) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
            )


            Text(text = forecast.main?.temp.toString().substring(0, 2) + " °",
                color = Secondary,
                fontSize = 16.sp,
                modifier = Modifier
                    .constrainAs(txtTemp) {
                        top.linkTo(icWeather.bottom, SMALL_MARGIN)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
            )

            Text(text = forecast.dt_txt?.substring(11, 16).toString(),
                color = Secondary,
                fontSize = 16.sp,
                modifier = Modifier
                    .constrainAs(txtTime) {
                        top.linkTo(txtTemp.bottom, SMALL_MARGIN)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
            )
        }
    }
}


@Preview
@Composable
fun ListTodayWeatherPreview() {
}