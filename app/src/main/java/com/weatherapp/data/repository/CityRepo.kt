package com.weatherapp.data.repository

import android.content.Context
import com.weatherapp.R
import com.weatherapp.data.model.City
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

class CityRepo @Inject constructor(
    @ApplicationContext val context: Context
) {

    fun getCities(): List<City> {
        context.apply {
            return listOf(
                City(getString(R.string.cairo)),
                City(getString(R.string.london)),
                City(getString(R.string.paris)),
                City(getString(R.string.san_francisco)),
                City(getString(R.string.washington)),
                City(getString(R.string.alexandria)),
                City(getString(R.string.luxor)),
                City(getString(R.string.giza)),
                City(getString(R.string.madrid)),
                City(getString(R.string.saint_petersburg)),
                City(getString(R.string.rome)),
                City(getString(R.string.kuwait)),
                City(getString(R.string.bilbeis))
            )
        }
    }
}