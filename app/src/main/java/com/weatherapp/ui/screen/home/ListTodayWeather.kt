package com.weatherapp.ui.screen.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.weatherapp.data.local.Constants.DEGREE
import com.weatherapp.data.local.Constants.IMAGE_URL
import com.weatherapp.data.local.Constants.SIZE
import com.weatherapp.data.model.forecast.ListItem
import com.weatherapp.ui.theme.SMALL_MARGIN

@ExperimentalCoilApi
@Composable
fun ListTodayWeather(forecast: ListItem) {

    Card(
        shape = RoundedCornerShape(30.dp),
        elevation = 10.dp,
        modifier = Modifier
            .padding(8.dp)
            .size(60.dp,110.dp),
        backgroundColor = MaterialTheme.colors.surface
    ) {

        ConstraintLayout {
            val (icWeather, txtTemp, txtTime) = createRefs()
            val lastIndex = forecast.main?.temp.toString().indexOf(".")

            Image(
                painter = rememberImagePainter(
                    data = "$IMAGE_URL${
                        forecast.weather?.get(0)?.icon
                    }$SIZE"
                ),
                contentDescription = "",
                modifier = Modifier
                    .constrainAs(icWeather) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }.size(40.dp)
            )


            Text(text = forecast.main?.temp.toString().substring(0, lastIndex) + " " + DEGREE,
                color = MaterialTheme.colors.primaryVariant,
                fontSize = 16.sp,
                modifier = Modifier
                    .constrainAs(txtTemp) {
                        top.linkTo(icWeather.bottom, SMALL_MARGIN)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
            )

            Text(text = forecast.dt_txt?.substring(11, 16).toString(),
                color = MaterialTheme.colors.primaryVariant,
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
