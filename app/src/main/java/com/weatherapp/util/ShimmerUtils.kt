package com.weatherapp.util

import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.weatherapp.presentation.theme.BIG_MARGIN
import com.weatherapp.presentation.theme.CUSTOM_MARGIN
import com.weatherapp.presentation.theme.LARGE_MARGIN
import com.weatherapp.presentation.theme.MEDIUM_MARGIN
import com.weatherapp.presentation.theme.SMALL_MARGIN

fun Modifier.shimmerEffect(): Modifier = composed {
    var size by remember {
        mutableStateOf(IntSize.Zero)
    }
    val transition = rememberInfiniteTransition(label = "")
    val startOffsetX by transition.animateFloat(
        initialValue = -2 * size.width.toFloat(),
        targetValue = 2 * size.width.toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(1000)
        ), label = ""
    )

    background(
        brush = Brush.linearGradient(
            colors = listOf(
                MaterialTheme.colors.surface,
                MaterialTheme.colors.surface.copy(alpha = 0.5f),
                MaterialTheme.colors.surface
            ),
            start = Offset(startOffsetX, 0f),
            end = Offset(startOffsetX + size.width.toFloat(), size.height.toFloat())
        )
    )
        .onGloballyPositioned {
            size = it.size
        }
}

@Composable
fun LoadingScreen() {

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
    ) {


        val (cityBox, tempBox, feelsLikeBox, weatherDesBox, iconBox, windSpeedBox,
            visibilityBox, moreBox, weatherRow
        ) = createRefs()

        Box(
            modifier = Modifier
                .constrainAs(cityBox) {
                    top.linkTo(parent.top, BIG_MARGIN)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .width(100.dp)
                .height(80.dp)
                .padding(start = SMALL_MARGIN, end = SMALL_MARGIN, bottom = BIG_MARGIN)
                .clip(shape = RoundedCornerShape(10.dp))
                .shimmerEffect()
        )


        Box(
            modifier = Modifier
                .constrainAs(tempBox) {
                    top.linkTo(cityBox.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .width(220.dp)
                .height(120.dp)
                .padding(start = SMALL_MARGIN, end = SMALL_MARGIN, bottom = CUSTOM_MARGIN)
                .clip(shape = RoundedCornerShape(10.dp))
                .shimmerEffect()
        )


        Box(
            modifier = Modifier
                .constrainAs(feelsLikeBox) {
                    top.linkTo(tempBox.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .width(130.dp)
                .height(20.dp)
                .padding(start = SMALL_MARGIN, end = SMALL_MARGIN, bottom = SMALL_MARGIN)
                .clip(shape = RoundedCornerShape(10.dp))
                .shimmerEffect()
        )


        Box(
            modifier = Modifier
                .constrainAs(weatherDesBox) {
                    top.linkTo(feelsLikeBox.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .width(100.dp)
                .height(20.dp)
                .padding(start = SMALL_MARGIN, end = SMALL_MARGIN, bottom = SMALL_MARGIN)
                .clip(shape = RoundedCornerShape(10.dp))
                .shimmerEffect()
        )



        Box(
            modifier = Modifier
                .constrainAs(iconBox) {
                    top.linkTo(weatherDesBox.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .width(40.dp)
                .height(30.dp)
                .padding(start = SMALL_MARGIN, end = SMALL_MARGIN, bottom = MEDIUM_MARGIN)
                .clip(shape = RoundedCornerShape(10.dp))
                .shimmerEffect()
        )


        Box(
            modifier = Modifier
                .constrainAs(windSpeedBox) {
                    top.linkTo(iconBox.bottom, LARGE_MARGIN)
                    start.linkTo(parent.start)
                }
                .width(130.dp)
                .height(30.dp)
                .padding(start = LARGE_MARGIN, bottom = MEDIUM_MARGIN)
                .clip(shape = RoundedCornerShape(10.dp))
                .shimmerEffect()
        )


        Box(
            modifier = Modifier
                .constrainAs(moreBox) {
                    top.linkTo(iconBox.bottom, LARGE_MARGIN)
                    end.linkTo(parent.end)
                }
                .width(80.dp)
                .height(30.dp)
                .padding(end = LARGE_MARGIN)
                .clip(shape = RoundedCornerShape(10.dp))
                .shimmerEffect()
        )


        Box(
            modifier = Modifier
                .constrainAs(visibilityBox) {
                    top.linkTo(windSpeedBox.bottom)
                    start.linkTo(parent.start)
                }
                .width(130.dp)
                .height(30.dp)
                .padding(start = LARGE_MARGIN)
                .clip(shape = RoundedCornerShape(10.dp))
                .shimmerEffect()
        )


        Box(
            modifier = Modifier
                .constrainAs(weatherRow) {
                    top.linkTo(visibilityBox.bottom, MEDIUM_MARGIN)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .fillMaxWidth()
                .height(150.dp)
                .padding(start = LARGE_MARGIN, end = LARGE_MARGIN)
                .clip(shape = RoundedCornerShape(10.dp))
                .shimmerEffect()
        )
    }
}