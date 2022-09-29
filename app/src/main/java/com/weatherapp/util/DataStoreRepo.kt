package com.weatherapp.util

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.weatherapp.data.local.Constants.PREFERENCE_NAME
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.map
import javax.inject.Inject

val Context.dataStore: DataStore<Preferences>
        by preferencesDataStore(name = PREFERENCE_NAME)

class DataStoreRepo @Inject constructor(
    @ApplicationContext val context: Context
) {
    private val dataStore = context.dataStore

    suspend fun writeState(key: Preferences.Key<String>, value: String) {
        dataStore.edit {
            it[key] = value
        }
    }

    fun readState(key: Preferences.Key<String>) =
        dataStore.data.map {
            it[key] ?: ""
        }
}