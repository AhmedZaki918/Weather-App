package com.weatherapp.util

import android.content.Context
import android.os.Build
import android.widget.Toast
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.weatherapp.R
import com.weatherapp.data.network.Resource
import com.weatherapp.ui.theme.Hint
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*


fun Context.toast(message: String?) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}


fun formatDate(dateFormat: String): String {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern(dateFormat)
        current.format(formatter)
    } else {
        val formatter = SimpleDateFormat(dateFormat, Locale.ENGLISH)
        formatter.format(Date())
    }
}


@Composable
fun Circle(
    modifier: Modifier,
    percentage: Float
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
                color = Hint,
                -90f,
                360 * currentPercentage.value,
                useCenter = false,
                style = Stroke(4.dp.toPx(), cap = StrokeCap.Round)
            )
        }
        Text(
            text = (currentPercentage.value * 100).toInt().toString() + "%",
            color = Color.White,
            fontSize = 20.sp,
        )
    }
}


fun Context.handleApiError(
    failure: Resource.Failure
) {
    when {
        failure.isNetworkError -> toast(getString(R.string.connection_error))
        failure.errorCode == 404 -> toast(getString(R.string.not_found))
        failure.errorCode == 422 -> toast(getString(R.string.invalid_auth))
        else -> toast(getString(R.string.not_found))
    }
}

