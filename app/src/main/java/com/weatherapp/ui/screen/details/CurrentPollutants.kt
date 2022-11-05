package com.weatherapp.ui.screen.details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.weatherapp.R
import com.weatherapp.data.local.Constants.UG_M3
import com.weatherapp.data.model.pollution.AirPollutionResponse
import com.weatherapp.data.viewmodel.DetailsViewModel
import com.weatherapp.ui.theme.LARGE_MARGIN
import com.weatherapp.ui.theme.MEDIUM_MARGIN
import com.weatherapp.ui.theme.SMALL_MARGIN
import com.weatherapp.util.Line


@Composable
fun CurrentPollutants(
    modifier: Modifier,
    viewModel: DetailsViewModel,
    airPollution: AirPollutionResponse?
) {

    ConstraintLayout(modifier = modifier) {
        val (txtHeader, dividerHeader, firstRow, secondRow) = createRefs()

        val components = airPollution?.list?.get(0)?.components
        val no2 = viewModel.getNo2Details(components?.no2)
        val pm25 = viewModel.getPm25Details(components?.pm2_5)
        val pm10 = viewModel.getPm10Details(components?.pm10)
        val ozone = viewModel.getOzoneDetails(components?.o3)


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
                status = ozone.status,
                color = ozone.color,
                ug_m3 = "${components?.o3} $UG_M3",
                description = stringResource(R.string.ozone),
                modifier = Modifier.padding(top = MEDIUM_MARGIN, end = SMALL_MARGIN)
            )
            // ParticulatesMatter_25
            Pollutant(
                title = "PM 25",
                status = pm25.status,
                color = pm25.color,
                ug_m3 = "${components?.pm2_5} $UG_M3",
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
                status = pm10.status,
                color = pm10.color,
                ug_m3 = "${components?.pm10} $UG_M3",
                description = stringResource(R.string.particulates_matter10),
                modifier = Modifier.padding(top = MEDIUM_MARGIN, end = SMALL_MARGIN)
            )

            // NitrogenDioxide
            Pollutant(
                title = "NO2",
                status = no2.status,
                color = no2.color,
                ug_m3 = "${components?.no2} $UG_M3",
                description = stringResource(R.string.nitrogen_dioxide),
                modifier = Modifier.padding(top = MEDIUM_MARGIN)
            )
        }
    }
}


@Composable
fun Pollutant(
    title: String,
    status: String,
    color: Color,
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