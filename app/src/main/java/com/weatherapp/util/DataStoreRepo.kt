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


    suspend fun writeString(key: Preferences.Key<String>, value: String) {
        dataStore.edit {
            it[key] = value
        }
    }

    suspend fun writeBoolean(key: Preferences.Key<Boolean>, value: Boolean) {
        dataStore.edit {
            it[key] = value
        }
    }

    suspend fun writeInt(key: Preferences.Key<Int>, value: Int) {
        dataStore.edit {
            it[key] = value
        }
    }


    fun readString(key: Preferences.Key<String>) =
        dataStore.data.map {
            it[key] ?: ""
        }

    fun readBoolean(key: Preferences.Key<Boolean>) =
        dataStore.data.map {
            it[key] ?: true
        }

    fun readInt(key: Preferences.Key<Int>) =
        dataStore.data.map {
            it[key] ?: 0
        }
}