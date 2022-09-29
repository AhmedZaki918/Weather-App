package com.weatherapp.ui.screen.city

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import com.weatherapp.R
import com.weatherapp.data.viewmodel.CityViewModel
import com.weatherapp.ui.theme.*
import com.weatherapp.util.Line
import com.weatherapp.util.toast

@ExperimentalMaterialApi
@Composable
fun CityScreen(
    viewModel: CityViewModel,
    navController: NavHostController
) {

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
    ) {
        item {
            Header(
                viewModel = viewModel,
                navController = navController
            )
        }
        items(viewModel.getAllCities()) {
            ListItemCity(
                city = it,
                viewModel = viewModel,
                navController = navController
            )
        }
    }
}


@Composable
fun Header(
    viewModel: CityViewModel,
    navController: NavHostController
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(HomeBackground)
    ) {

        val context = LocalContext.current
        val (txtCity, txtCaption, tfCity, btnAdd, txtSuggested, line, icon) = createRefs()
        var textFieldState by remember { mutableStateOf("") }

        Text(
            modifier = Modifier.constrainAs(txtCity) {
                top.linkTo(parent.top, LARGE_MARGIN)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            },
            text = stringResource(R.string.city),
            fontSize = 25.sp,
            color = Color.White
        )

        Text(
            modifier = Modifier
                .constrainAs(txtCaption) {
                    top.linkTo(txtCity.bottom, MEDIUM_MARGIN)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .padding(start = LARGE_MARGIN, end = LARGE_MARGIN),
            text = stringResource(id = R.string.city_caption),
            fontSize = 16.sp,
            lineHeight = 25.sp,
            textAlign = TextAlign.Center,
            color = Secondary
        )

        TextField(
            modifier = Modifier
                .constrainAs(tfCity) {
                    top.linkTo(txtCaption.bottom, LARGE_MARGIN)
                    start.linkTo(parent.start, MEDIUM_MARGIN)
                }
                .background(BottomSheet),
            value = textFieldState,
            onValueChange = {
                textFieldState = it
            },
            placeholder = {
                Text(
                    modifier = Modifier.alpha(ContentAlpha.medium),
                    text = stringResource(id = R.string.enter_city),
                    color = Color.White
                )
            },
            textStyle = TextStyle(color = Secondary),
            singleLine = true
        )

        Button(modifier = Modifier
            .constrainAs(btnAdd) {
                top.linkTo(tfCity.top, VERY_SMALL_MARGIN)
                start.linkTo(tfCity.end, MEDIUM_MARGIN)
            },
            colors = ButtonDefaults.buttonColors(backgroundColor = Hint),
            onClick = {
                viewModel.saveCity(textFieldState)
                context.toast("$textFieldState ${context.resources.getString(R.string.city_saved)}")
                navController.navigateUp()
            }) {
            Text(
                text = stringResource(id = R.string.add),
                color = Color.Black, fontSize = 16.sp
            )
        }

        Text(
            modifier = Modifier
                .constrainAs(txtSuggested) {
                    top.linkTo(tfCity.bottom, BIG_MARGIN)
                    start.linkTo(icon.end, SMALL_MARGIN)
                },
            text = stringResource(R.string.suggested),
            color = Secondary
        )

        Icon(
            modifier = Modifier
                .constrainAs(icon) {
                    top.linkTo(txtSuggested.top)
                    start.linkTo(parent.start, LARGE_MARGIN)
                },
            painter = painterResource(id = R.drawable.ic_outline_location_city),
            tint = Secondary,
            contentDescription = ""
        )

        Line(modifier = Modifier
            .constrainAs(line) {
                top.linkTo(txtSuggested.bottom, SMALL_MARGIN)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
            .padding(
                start = LARGE_MARGIN,
                end = LARGE_MARGIN,
                bottom = SMALL_MARGIN
            ))
    }
}