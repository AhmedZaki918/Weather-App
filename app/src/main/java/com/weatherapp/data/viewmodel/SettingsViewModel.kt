package com.weatherapp.data.viewmodel

import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.weatherapp.util.DataStoreRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val dataStore: DataStoreRepo
) : ViewModel() {

    fun writeData(key: Preferences.Key<String>, value: String) {
        viewModelScope.launch {
            dataStore.writeState(key, value)
        }
    }

    fun readData(key: Preferences.Key<String>) =
        dataStore.readState(key)
}