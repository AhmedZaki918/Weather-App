package com.weatherapp.data.repository

import com.weatherapp.data.model.City
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

class CityRepo @Inject constructor() {

    fun getCities() =
         listOf(
            City("Cairo"),
            City("London"),
            City("Paris"),
            City("San Francisco"),
            City("Washington"),
            City("Alexandria"),
             City("Cairo"),
             City("London"),
             City("Paris"),
             City("San Francisco"),
             City("Washington"),
             City("Alexandria")
        )
}