package com.weatherapp

import android.content.Context
import android.widget.Toast
import com.weatherapp.data.network.Resource


fun Context.toast(message: String?) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
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


