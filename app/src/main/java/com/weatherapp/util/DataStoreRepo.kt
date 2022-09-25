package com.weatherapp.util

import android.content.Context
import androidx.compose.ui.res.stringResource
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.weatherapp.data.local.Constants.PREFERENCE_KEY
import com.weatherapp.data.local.Constants.PREFERENCE_NAME
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = PREFERENCE_NAME)

class DataStoreRepo @Inject constructor(
    @ApplicationContext val context: Context
) {

    private object PreferenceKeys {
        val tempUnit = stringPreferencesKey(name = PREFERENCE_KEY)
    }

    private val dataStore = context.dataStore

    suspend fun writeState(data: String){
        dataStore.edit {
            it[PreferenceKeys.tempUnit] = data
        }
    }

    val readState: Flow<String> =dataStore.data.map {
        it[PreferenceKeys.tempUnit] ?: ""
    }
}