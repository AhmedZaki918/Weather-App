package com.weatherapp.ui.screen.home

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.weatherapp.R
import com.weatherapp.data.model.TodayWeather
import com.weatherapp.ui.theme.ListContent
import com.weatherapp.ui.theme.SMALL_MARGIN
import com.weatherapp.ui.theme.Secondary

@Composable
fun ListTodayWeather(todayWeather: TodayWeather) {


    Card(
        shape = RoundedCornerShape(30.dp),
        elevation = 10.dp,
        modifier = Modifier.padding(8.dp).size(60.dp,110.dp),
        backgroundColor = ListContent
    ) {

        ConstraintLayout {
            val (icWeather, txtTemp, txtTime) = createRefs()


            Icon(
                painter = painterResource(id = todayWeather.icon),
                contentDescription = "",
                tint = Color.White
                ,
                modifier = Modifier
                    .constrainAs(icWeather) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
            )


            Text(text = todayWeather.temp,
                color = Secondary,
                fontSize = 16.sp,
                modifier = Modifier
                    .constrainAs(txtTemp) {
                        top.linkTo(icWeather.bottom, SMALL_MARGIN)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
            )

            Text(text = todayWeather.time,
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
    ListTodayWeather(
        todayWeather = TodayWeather(R.drawable.preview_cloudy, "25°", "06:00")
    )
}