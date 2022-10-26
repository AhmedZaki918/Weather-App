package com.weatherapp.ui.screen.details

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.weatherapp.R
import com.weatherapp.data.local.Constants
import com.weatherapp.data.repository.DetailsRepo
import com.weatherapp.ui.theme.*
import com.weatherapp.util.Line
import com.weatherapp.util.formatDate

@Composable
fun DetailsScreen() {

    val fakeData = DetailsRepo().getData()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .padding(bottom = BIG_MARGIN)
    ) {
        item {
            Header()
        }
        items(fakeData) {
            FakeListItem(data = it)
        }
    }
}


@Composable
fun Header() {
    ConstraintLayout {

        val (txtTitle, divider, txtToday, circleAQI, txtDescriptionAQI,
            txtMoreDes, txtTitlePollutants, txtTitleFiveDays, secondDivider) = createRefs()

        val aqi = 2
        val percentageAirQuality: Float
        val colorAirQuality: Color
        val desAirQuality: String


        when (aqi) {
            1 -> {
                percentageAirQuality = 0.2f
                colorAirQuality = AQI_Green
                desAirQuality = stringResource(R.string.good)
            }
            2 -> {
                percentageAirQuality = 0.4f
                colorAirQuality = AQI_Yellow
                desAirQuality = stringResource(R.string.fair)
            }
            3 -> {
                percentageAirQuality = 0.6f
                colorAirQuality = AQI_Orange
                desAirQuality = stringResource(R.string.moderate)
            }
            4 -> {
                percentageAirQuality = 0.8f
                colorAirQuality = AQI_Red
                desAirQuality = stringResource(R.string.poor)
            }
            else -> {
                percentageAirQuality = 1f
                colorAirQuality = AQI_RedDark
                desAirQuality = stringResource(R.string.very_poor)
            }
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
                    append(formatDate(Constants.FORMAT_TYPE))
                }
            },
            modifier = Modifier
                .constrainAs(txtToday) {
                    start.linkTo(parent.start, LARGE_MARGIN)
                    top.linkTo(divider.bottom, MEDIUM_MARGIN)
                }
        )

        CircleCustom(
            modifier = Modifier.constrainAs(circleAQI) {
                top.linkTo(txtToday.bottom, MEDIUM_MARGIN)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            },
            percentage = percentageAirQuality,
            colorAirQuality = colorAirQuality
        )


        Text(
            modifier = Modifier.constrainAs(txtDescriptionAQI) {
                top.linkTo(circleAQI.bottom, MEDIUM_MARGIN)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }, text = desAirQuality,
            color = MaterialTheme.colors.primary,
            fontSize = 18.sp
        )

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

        CurrentPollutants(modifier = Modifier.constrainAs(txtTitlePollutants) {
            top.linkTo(txtMoreDes.bottom, BIG_MARGIN)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        })


        Text(
            modifier = Modifier.constrainAs(txtTitleFiveDays) {
                top.linkTo(txtTitlePollutants.bottom, BIG_MARGIN)
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


@Preview
@Composable
fun DetailsScreenPreview() {
    DetailsScreen()
}