package com.weatherapp.data.viewmodel

import android.app.Application
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.weatherapp.R
import com.weatherapp.util.DataStoreRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val dataStore: DataStoreRepo,
    application: Application
) : AndroidViewModel(application) {

    val context = getApplication<Application>()

    fun saveBoolean(key: Preferences.Key<Boolean>, value: Boolean) {
        viewModelScope.launch {
            dataStore.writeBoolean(key, value)
        }
    }

    fun saveInt(key: Preferences.Key<Int>, value: Int) {
        viewModelScope.launch {
            dataStore.writeInt(key, value)
        }
    }

    fun retrieveString(key: Preferences.Key<String>) =
        dataStore.readString(key)

    fun retrieveBoolean(key: Preferences.Key<Boolean>) =
        dataStore.readBoolean(key)

    fun retrieveInt(key: Preferences.Key<Int>) =
        dataStore.readInt(key)


    fun selectedCity(index: Int):String {
        return when (index) {
            0 -> context.getString(R.string.cairo)
            1 -> context.getString(R.string.london)
            2 -> context.getString(R.string.paris)
            3 -> context.getString(R.string.san_francisco)
            4 -> context.getString(R.string.washington)
            5 -> context.getString(R.string.alexandria)
            6 -> context.getString(R.string.luxor)
            7 -> context.getString(R.string.giza)
            8 -> context.getString(R.string.madrid)
            9 -> context.getString(R.string.saint_petersburg)
            10 -> context.getString(R.string.rome)
            11 -> context.getString(R.string.kuwait)
            12 -> context.getString(R.string.pakistan)
            else -> context.getString(R.string.user_preference)
        }
    }
}