package com.weatherapp.presentation.details

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.weatherapp.R
import com.weatherapp.data.local.Constants.UG_M3
import com.weatherapp.data.model.pollution.AirPollutionResponse
import com.weatherapp.presentation.theme.LARGE_MARGIN
import com.weatherapp.presentation.theme.MEDIUM_MARGIN
import com.weatherapp.presentation.theme.SMALL_MARGIN
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
                start.linkTo(parent.start)
            },
            text = stringResource(R.string.current_pollutants),
            fontSize = 18.sp,
            color = MaterialTheme.colors.primary
        )

        Line(
            modifier = Modifier
                .constrainAs(dividerHeader) {
                    top.linkTo(txtHeader.bottom, SMALL_MARGIN)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }, thickness = 1.dp
        )


        // First Row
        Row(modifier = Modifier
            .fillMaxWidth()
            .constrainAs(firstRow) {
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
                modifier = Modifier.fillMaxWidth(0.5f)

            )
            // ParticulatesMatter_25
            Pollutant(
                title = "PM 25",
                status = pm25.status,
                color = pm25.color,
                ug_m3 = "${components?.pm2_5} $UG_M3",
                description = stringResource(R.string.particulates_matter2_5),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = MEDIUM_MARGIN)
            )
        }


        // Second Row
        Row(modifier = Modifier
            .fillMaxWidth()
            .constrainAs(secondRow) {
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
                modifier = Modifier.fillMaxWidth(0.5f)
            )

            // NitrogenDioxide
            Pollutant(
                title = "NO2",
                status = no2.status,
                color = no2.color,
                ug_m3 = "${components?.no2} $UG_M3",
                description = stringResource(R.string.nitrogen_dioxide),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = MEDIUM_MARGIN)
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
        modifier = modifier
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
            color = MaterialTheme.colors.primary,
            fontWeight = FontWeight.Bold
        )

        Text(
            modifier = Modifier
                .padding(top = MEDIUM_MARGIN)
                .fillMaxWidth(0.85f),
            text = description,
            color = MaterialTheme.colors.primaryVariant,
            fontSize = 14.sp,
            lineHeight = 20.sp,
            maxLines = 6,
            overflow = TextOverflow.Ellipsis
        )
    }
}