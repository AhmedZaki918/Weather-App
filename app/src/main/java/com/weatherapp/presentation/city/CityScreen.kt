package com.weatherapp.presentation.city

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import com.weatherapp.R
import com.weatherapp.presentation.theme.BIG_MARGIN
import com.weatherapp.presentation.theme.LARGE_MARGIN
import com.weatherapp.presentation.theme.MEDIUM_MARGIN
import com.weatherapp.presentation.theme.SMALL_MARGIN
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
            .background(MaterialTheme.colors.background)
    ) {

        val context = LocalContext.current
        val (txtCity, txtCaption, txtSuggested, line, icon, row) = createRefs()
        var textFieldState by remember { mutableStateOf("") }

        Text(
            modifier = Modifier.constrainAs(txtCity) {
                top.linkTo(parent.top, LARGE_MARGIN)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            },
            text = stringResource(R.string.city),
            fontSize = 25.sp,
            color = MaterialTheme.colors.primary
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
            color = MaterialTheme.colors.primaryVariant
        )


        Row(modifier = Modifier
            .constrainAs(row) {
                top.linkTo(txtCaption.bottom, LARGE_MARGIN)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }.padding(start = LARGE_MARGIN, end = LARGE_MARGIN)) {

            TextField(
                modifier = Modifier
                    .weight(0.6f,false)
                    .background(MaterialTheme.colors.surface),
                value = textFieldState,
                onValueChange = {
                    textFieldState = it
                },
                placeholder = {
                    Text(
                        modifier = Modifier.alpha(ContentAlpha.medium),
                        text = stringResource(id = R.string.enter_city),
                        color = MaterialTheme.colors.primary
                    )
                },
                textStyle = TextStyle(color = MaterialTheme.colors.primaryVariant),
                singleLine = true
            )


            Button(modifier = Modifier.padding(start = MEDIUM_MARGIN),
                colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.secondary),
                onClick = {
                    viewModel.apply {
                        saveCityName(textFieldState)
                        // 13 index means the city has been added outside the suggested cities list,
                        // means user preference too.
                        saveCityIndex(13)
                    }
                    context.toast("$textFieldState ${context.resources.getString(R.string.city_saved)}")
                    navController.navigateUp()
                }) {
                Text(
                    text = stringResource(id = R.string.add),
                    color = MaterialTheme.colors.onSecondary, fontSize = 16.sp,
                )
            }
        }


        Text(
            modifier = Modifier
                .constrainAs(txtSuggested) {
                    top.linkTo(row.bottom, BIG_MARGIN)
                    start.linkTo(icon.end, SMALL_MARGIN)
                },
            text = stringResource(R.string.suggested),
            color = MaterialTheme.colors.primaryVariant
        )

        Icon(
            modifier = Modifier
                .constrainAs(icon) {
                    top.linkTo(txtSuggested.top)
                    start.linkTo(parent.start, LARGE_MARGIN)
                },
            painter = painterResource(id = R.drawable.ic_outline_location_city),
            tint = MaterialTheme.colors.primaryVariant,
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