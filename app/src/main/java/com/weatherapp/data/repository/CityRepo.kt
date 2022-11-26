package com.weatherapp.data.repository

import android.content.Context
import com.weatherapp.R
import com.weatherapp.data.model.City
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class CityRepo @Inject constructor(
    @ApplicationContext val context: Context
) {

    fun getCities(): List<City> {
        context.apply {
            return listOf(
                City(0,getString(R.string.cairo)),
                City(1,getString(R.string.london)),
                City(2,getString(R.string.paris)),
                City(3,getString(R.string.san_francisco)),
                City(4,getString(R.string.washington)),
                City(5,getString(R.string.alexandria)),
                City(6,getString(R.string.luxor)),
                City(7,getString(R.string.giza)),
                City(8,getString(R.string.madrid)),
                City(9,getString(R.string.saint_petersburg)),
                City(10,getString(R.string.rome)),
                City(11,getString(R.string.kuwait)),
                City(12,getString(R.string.pakistan))
            )
        }
    }
}