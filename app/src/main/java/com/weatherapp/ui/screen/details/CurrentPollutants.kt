package com.weatherapp.ui.screen.details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.weatherapp.R
import com.weatherapp.ui.theme.*
import com.weatherapp.util.Circle
import com.weatherapp.util.Line


@Composable
fun CurrentPollutants(modifier: Modifier) {

    ConstraintLayout(modifier = modifier) {
        val (txtHeader, dividerHeader, firstRow, secondRow, circleCloudiness,
            txtCloudinessTitle, dividerCloudiness, iconCloud) = createRefs()

        // Header
        Text(
            modifier = Modifier.constrainAs(txtHeader) {
                top.linkTo(parent.top)
                start.linkTo(parent.start, LARGE_MARGIN)
            },
            text = stringResource(R.string.current_pollutants),
            fontSize = 18.sp,
            color = MaterialTheme.colors.primary
        )


        // Line
        Line(modifier = Modifier
            .constrainAs(dividerHeader) {
                top.linkTo(txtHeader.bottom, SMALL_MARGIN)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
            .padding(start = LARGE_MARGIN, end = LARGE_MARGIN), thickness = 1.dp
        )


        // First Row
        Row(modifier = Modifier.constrainAs(firstRow) {
            top.linkTo(dividerHeader.bottom, MEDIUM_MARGIN)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }) {

            //Ozone
            Pollutant(
                title = "O3",
                color = AQI_Green,
                status = "Good",
                ug_m3 = "46 ug/m3",
                description = stringResource(R.string.ozone),
                modifier = Modifier.padding(top = MEDIUM_MARGIN, end = SMALL_MARGIN)
            )
            // ParticulatesMatter_25
            Pollutant(
                title = "PM 25",
                color = AQI_Yellow,
                status = "Fair",
                ug_m3 = "33 ug/m3",
                description = stringResource(R.string.particulates_matter2_5),
                modifier = Modifier.padding(top = MEDIUM_MARGIN)
            )
        }


        // Second Row
        Row(modifier = Modifier.constrainAs(secondRow) {
            top.linkTo(firstRow.bottom, LARGE_MARGIN)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }) {

            // ParticulatesMatter_10
            Pollutant(
                title = "PM 10",
                color = AQI_Yellow,
                status = "Fair",
                ug_m3 = "26 ug/m3",
                description = stringResource(R.string.particulates_matter10),
                modifier = Modifier.padding(top = MEDIUM_MARGIN, end = SMALL_MARGIN)
            )

            // NitrogenDioxide
            Pollutant(
                title = "NO2",
                color = AQI_Yellow,
                status = "Fair",
                ug_m3 = "5 ug/m3",
                description = stringResource(R.string.nitrogen_dioxide),
                modifier = Modifier.padding(top = MEDIUM_MARGIN)
            )
        }

        // Current cloudiness title
        Text(
            modifier = Modifier.constrainAs(txtCloudinessTitle) {
                top.linkTo(secondRow.bottom, BIG_MARGIN)
                start.linkTo(parent.start, LARGE_MARGIN)
            },
            text = stringResource(R.string.current_cloudiness),
            fontSize = 18.sp,
            color = MaterialTheme.colors.primary
        )

        Icon(
            modifier = Modifier.constrainAs(iconCloud) {
                top.linkTo(txtCloudinessTitle.top)
                bottom.linkTo(txtCloudinessTitle.bottom)
                start.linkTo(txtCloudinessTitle.end, SMALL_MARGIN)
            }, painter = painterResource(id = R.drawable.preview_all_cloud),
            contentDescription = "",
            tint = MaterialTheme.colors.primary
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
                bottom.linkTo(parent.bottom, MEDIUM_MARGIN)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            },
            percentage = 0.6f,
            arcColor = MaterialTheme.colors.secondary
        )
    }
}


@Composable
fun Pollutant(
    title: String,
    color: Color,
    status: String,
    ug_m3: String,
    description: String,
    modifier: Modifier
) {
    Column(
        modifier = Modifier
            .padding(start = MEDIUM_MARGIN)
            .width(170.dp)
    ) {
        Text(
            text = title,
            color = MaterialTheme.colors.primaryVariant,
            fontSize = 18.sp
        )

        Line(
            modifier = Modifier
                .width(40.dp)
                .padding(top = SMALL_MARGIN),
            thickness = 3.dp,
            color = color
        )

        Text(
            modifier = Modifier.padding(top = SMALL_MARGIN),
            text = status,
            color = MaterialTheme.colors.primary,
            fontSize = 18.sp
        )

        Text(
            modifier = Modifier.padding(top = MEDIUM_MARGIN),
            text = ug_m3,
            color = MaterialTheme.colors.primary
        )

        Text(
            modifier = modifier,
            text = description,
            color = MaterialTheme.colors.primaryVariant,
            fontSize = 14.sp,
            lineHeight = 20.sp
        )
    }
}