package com.weatherapp

import android.content.Context
import android.os.Build
import android.widget.Toast
import com.weatherapp.data.network.Resource
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


