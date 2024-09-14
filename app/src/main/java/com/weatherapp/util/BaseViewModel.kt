package com.weatherapp.util

import androidx.lifecycle.ViewModel

abstract class BaseViewModel<T> : ViewModel() {
    abstract fun onIntent(intent: T)
}