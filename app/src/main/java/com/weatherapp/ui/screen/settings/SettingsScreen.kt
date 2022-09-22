package com.weatherapp.ui.screen.settings

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.weatherapp.R
import com.weatherapp.data.local.Constants.LANG
import com.weatherapp.data.local.Constants.TEMP
import com.weatherapp.ui.theme.*
import com.weatherapp.util.Line
import kotlinx.coroutines.launch

@ExperimentalMaterialApi
@Composable
fun SettingsScreen() {

    val scope = rememberCoroutineScope()
    val sheetState = rememberBottomSheetState(
        initialValue = BottomSheetValue.Collapsed
    )
    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = sheetState
    )

    var bottomSheetType by remember {
        mutableStateOf("")
    }

    val languageState = remember {
        mutableStateOf(false)
    }
    if (languageState.value) {
        //SetLocale()
    }


    BottomSheetScaffold(
        sheetShape = Shapes.small,
        scaffoldState = scaffoldState,
        sheetContent = {

            if (bottomSheetType == LANG) {
                val languages =
                    listOf(
                        stringResource(R.string.arabic),
                        stringResource(R.string.english),
                        stringResource(R.string.french),
                        stringResource(R.string.italy),
                        stringResource(R.string.spanish),
                        stringResource(R.string.germany)
                    )
                BottomSheetContent(
                    stringResource(R.string.language),
                    sheetState,
                    550.dp,
                    languages,
                    languageState
                )

            } else {
                val units = listOf(
                    stringResource(R.string.celsius),
                    stringResource(R.string.fahrenheit)
                )
                BottomSheetContent(
                    stringResource(R.string.temperature),
                    sheetState,
                    290.dp,
                    units,
                    languageState
                )
            }
        },
        sheetBackgroundColor = BottomSheet,
        sheetPeekHeight = 0.dp
    ) {


        // This is the main content of settings screen
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .background(HomeBackground)
        ) {

            val (txtSetting, btnTemp, btnLanguage, btnCity,
                line, secondLine, thirdLine) = createRefs()


            Text(
                modifier = Modifier.constrainAs(txtSetting) {
                    top.linkTo(parent.top, LARGE_MARGIN)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
                text = stringResource(R.string.settings),
                fontSize = 25.sp,
                color = Color.White
            )

            CustomButton(modifier = Modifier
                .constrainAs(btnTemp) {
                    top.linkTo(txtSetting.bottom, BIG_MARGIN)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .padding(start = MEDIUM_MARGIN, end = MEDIUM_MARGIN)
                .fillMaxWidth(),
                title = stringResource(R.string.temp_unit),
                onButtonClicked = {
                    bottomSheetType = TEMP
                    scope.launch {
                        if (sheetState.isCollapsed) sheetState.expand()
                    }
                })


            Line(modifier = Modifier
                .constrainAs(line) {
                    top.linkTo(btnTemp.bottom, MEDIUM_MARGIN)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .padding(start = LARGE_MARGIN, end = LARGE_MARGIN)
            )


            CustomButton(modifier = Modifier
                .constrainAs(btnLanguage) {
                    top.linkTo(line.bottom, MEDIUM_MARGIN)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .padding(start = MEDIUM_MARGIN, end = MEDIUM_MARGIN)
                .fillMaxWidth(),
                title = stringResource(R.string.language),
                onButtonClicked = {
                    bottomSheetType = LANG
                    scope.launch {
                        if (sheetState.isCollapsed) sheetState.expand()
                    }
                })


            Line(modifier = Modifier
                .constrainAs(secondLine) {
                    top.linkTo(btnLanguage.bottom, MEDIUM_MARGIN)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .padding(start = LARGE_MARGIN, end = LARGE_MARGIN)
            )


            CustomButton(modifier = Modifier
                .constrainAs(btnCity) {
                    top.linkTo(secondLine.bottom, MEDIUM_MARGIN)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .padding(start = MEDIUM_MARGIN, end = MEDIUM_MARGIN)
                .fillMaxWidth(),
                title = stringResource(R.string.city),
                onButtonClicked = {})

            Line(modifier = Modifier
                .constrainAs(thirdLine) {
                    top.linkTo(btnCity.bottom, MEDIUM_MARGIN)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .padding(start = LARGE_MARGIN, end = LARGE_MARGIN)
            )
        }
    }
}


@ExperimentalMaterialApi
@Composable
fun BottomSheetContent(
    title: String,
    sheetState: BottomSheetState,
    height: Dp,
    data: List<String>,
    languageState: MutableState<Boolean>
) {

    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .height(height)
    ) {

        val scope = rememberCoroutineScope()
        val (txtHeader, btnFooter, rgTemperature, topHandler) = createRefs()

        Line(
            modifier = Modifier
                .constrainAs(topHandler) {
                    top.linkTo(parent.top, VERY_SMALL_MARGIN)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .width(30.dp),
            thickness = 3.dp,
            color = Color.LightGray.copy(alpha = 0.4f)
        )

        Text(
            modifier = Modifier
                .constrainAs(txtHeader) {
                    top.linkTo(parent.top, MEDIUM_MARGIN)
                    start.linkTo(parent.start, MEDIUM_MARGIN)
                },
            text = title,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
        )

        val (selectedOption, onOptionSelected) = remember { mutableStateOf(data[1]) }
        Column(modifier = Modifier
            .constrainAs(rgTemperature) {
                top.linkTo(txtHeader.bottom, MEDIUM_MARGIN)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        ) {
            data.forEach { text ->
                Row(
                    Modifier
                        .fillMaxWidth()
                        .selectable(
                            selected = (text == selectedOption),
                            onClick = {
                                onOptionSelected(text)
                                scope.launch { sheetState.collapse() }
                                languageState.value = true
                            }
                        )
                        .padding(horizontal = MEDIUM_MARGIN)
                ) {

                    Text(
                        text = text,
                        color = Secondary,
                        style = MaterialTheme.typography.body1.merge(),
                        modifier = Modifier
                            .padding(start = MEDIUM_MARGIN, top = MEDIUM_MARGIN)
                            .weight(8f)
                    )

                    RadioButton(
                        modifier = Modifier.weight(2f),
                        selected = (text == selectedOption),
                        onClick = {
                            onOptionSelected(text)
                            scope.launch { sheetState.collapse() }
                            languageState.value = true
                        },
                        colors = RadioButtonDefaults.colors(
                            unselectedColor = Secondary.copy(alpha = 0.3f)
                        )
                    )
                }

                // Don't draw line after last item in the list
                if (text != data.last()) {
                    Line(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                start = LARGE_MARGIN,
                                end = LARGE_MARGIN,
                                top = SMALL_MARGIN,
                                bottom = SMALL_MARGIN
                            )
                    )
                }
            }
        }

        Button(
            onClick = {
                scope.launch {
                    sheetState.collapse()
                }
            },
            modifier = Modifier
                .constrainAs(btnFooter) {
                    top.linkTo(rgTemperature.bottom, SMALL_MARGIN)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }, colors = ButtonDefaults.buttonColors(Color.Transparent),
            elevation = null
        ) {
            Text(text = stringResource(id = R.string.cancel), color = Color.White)
        }
    }
}



@Composable
fun CustomButton(
    modifier: Modifier,
    title: String,
    onButtonClicked: () -> Unit
) {
    Button(
        elevation = null,
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(backgroundColor = HomeBackground),
        onClick = {
            onButtonClicked()
        }) {
        Text(
            modifier = Modifier.weight(9f),
            text = title,
            color = Secondary,
            fontSize = 18.sp
        )

        Image(
            modifier = Modifier.weight(1f),
            painter = painterResource(id = R.drawable.ic_keyboard_arrow_right),
            contentDescription = ""
        )
    }
}


@ExperimentalMaterialApi
@Preview
@Composable
fun SettingsScreenPreview() {
    SettingsScreen()
}