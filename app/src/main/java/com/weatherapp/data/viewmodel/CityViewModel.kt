package com.weatherapp.data.viewmodel

import androidx.lifecycle.ViewModel
import com.weatherapp.data.repository.CityRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CityViewModel @Inject constructor(
    private val repo: CityRepo
) : ViewModel() {

    fun getAllCities() =
        repo.getCities()

}