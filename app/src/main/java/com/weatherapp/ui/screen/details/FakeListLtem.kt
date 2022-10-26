package com.weatherapp.ui.screen.details

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.weatherapp.R
import com.weatherapp.data.local.Constants
import com.weatherapp.data.model.FakeData
import com.weatherapp.ui.theme.CUSTOM_MARGIN
import com.weatherapp.ui.theme.LARGE_MARGIN
import com.weatherapp.ui.theme.SMALL_MARGIN
import com.weatherapp.util.Line


@Composable
fun FakeListItem(data: FakeData) {

    ConstraintLayout(modifier = Modifier.fillMaxWidth()) {
        val (txtDateTime, imageWeather, txtWeather, txtTemp, line) = createRefs()


        Text(
            buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        color = MaterialTheme.colors.primaryVariant,
                        fontSize = 14.sp
                    )
                ) {
                    append("${data.date}\n")
                }
                withStyle(
                    style = SpanStyle(
                        color = MaterialTheme.colors.secondary,
                        fontSize = 16.sp
                    )
                ) {
                    append(data.time.toString())
                }
            },
            modifier = Modifier
                .constrainAs(txtDateTime) {
                    start.linkTo(parent.start, LARGE_MARGIN)
                    top.linkTo(parent.top, CUSTOM_MARGIN)
                }
        )

        Image(painter = painterResource(id = R.drawable.preview_all_cloud),
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
            text = data.des.toString(),
            fontSize = 14.sp,
            color = MaterialTheme.colors.primaryVariant,
            modifier = Modifier
                .constrainAs(txtWeather) {
                    start.linkTo(imageWeather.end, SMALL_MARGIN)
                    top.linkTo(imageWeather.top)
                    bottom.linkTo(imageWeather.bottom)
                }
        )


        Text(
            text = data.temp + Constants.DEGREE,
            fontSize = 20.sp,
            color = MaterialTheme.colors.primaryVariant,
            modifier = Modifier
                .constrainAs(txtTemp) {
                    top.linkTo(txtWeather.top)
                    end.linkTo(parent.end, LARGE_MARGIN)
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