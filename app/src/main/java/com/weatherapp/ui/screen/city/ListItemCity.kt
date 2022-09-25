package com.weatherapp.ui.screen.city

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import com.weatherapp.R
import com.weatherapp.data.model.City
import com.weatherapp.data.viewmodel.CityViewModel
import com.weatherapp.ui.theme.HomeBackground
import com.weatherapp.ui.theme.LARGE_MARGIN
import com.weatherapp.ui.theme.MEDIUM_MARGIN
import com.weatherapp.ui.theme.Secondary
import com.weatherapp.util.Line
import com.weatherapp.util.toast


@ExperimentalMaterialApi
@Composable
fun ListItemCity(
    city: City,
    viewModel: CityViewModel,
    navController: NavHostController
) {

    val context = LocalContext.current
    Surface(modifier = Modifier.fillMaxWidth(),
        onClick = {
            viewModel.saveCity(city.name)
            context.toast("${city.name} ${context.resources.getString(R.string.city_saved)}")
            navController.navigateUp()
        }) {

        ConstraintLayout(
            modifier = Modifier.background(HomeBackground)
        ) {
            val (txtCity, line, icon) = createRefs()

            Text(
                modifier = Modifier.constrainAs(txtCity) {
                    top.linkTo(parent.top, MEDIUM_MARGIN)
                    start.linkTo(parent.start, LARGE_MARGIN)
                },
                text = city.name,
                color = Secondary,
                fontSize = 16.sp
            )

            Image(
                modifier = Modifier.constrainAs(icon) {
                    top.linkTo(txtCity.top)
                    end.linkTo(parent.end, LARGE_MARGIN)
                },
                painter = painterResource(id = R.drawable.ic_keyboard_arrow_right),
                contentDescription = "",
                alpha = 0.7f
            )

            Line(modifier = Modifier
                .constrainAs(line) {
                    top.linkTo(txtCity.bottom, MEDIUM_MARGIN)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .padding(start = LARGE_MARGIN, end = LARGE_MARGIN))
        }
    }
}


@ExperimentalMaterialApi
@Preview
@Composable
fun ListItemCityPreview() {
//    ListItemCity(
//        City("Cairo"),
//    )
}