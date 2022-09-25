package com.weatherapp.data.viewmodel

import androidx.lifecycle.ViewModel
import com.weatherapp.util.DataStoreRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val dataStore: DataStoreRepo
) : ViewModel() {

    fun readCity() = dataStore.readState
}