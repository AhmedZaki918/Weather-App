package com.weatherapp.data.repository

import com.weatherapp.R
import com.weatherapp.data.local.Constants
import com.weatherapp.data.local.Constants.DEGREE
import com.weatherapp.data.model.FakeData

class DetailsRepo {

    fun getData() : List<FakeData>{
        return listOf(
            FakeData(date = "2022-10-26", time = "12:00", R.drawable.preview_all_cloud, des = "clear sky", "12$DEGREE"),
            FakeData(date = "2022-10-26", time = "15:00", R.drawable.preview_all_cloud, des = "few clouds","15$DEGREE"),
            FakeData(date = "2022-10-26", time = "18:00", R.drawable.preview_all_cloud, des = "broken clouds","19$DEGREE"),
            FakeData(date = "2022-10-26", time = "21:00", R.drawable.preview_all_cloud, des = "broken clouds","22$DEGREE"),
            FakeData(date = "2022-10-27", time = "00:00", R.drawable.preview_all_cloud, des = "few clouds","15$DEGREE"),
            FakeData(date = "2022-10-26", time = "12:00", R.drawable.preview_all_cloud, des = "clear sky", "12$DEGREE"),
            FakeData(date = "2022-10-26", time = "15:00", R.drawable.preview_all_cloud, des = "few clouds","15$DEGREE"),
            FakeData(date = "2022-10-26", time = "18:00", R.drawable.preview_all_cloud, des = "broken clouds","19$DEGREE"),
            FakeData(date = "2022-10-26", time = "21:00", R.drawable.preview_all_cloud, des = "broken clouds","22$DEGREE"),
            FakeData(date = "2022-10-27", time = "00:00", R.drawable.preview_all_cloud, des = "few clouds","15$DEGREE"),
            FakeData(date = "2022-10-26", time = "12:00", R.drawable.preview_all_cloud, des = "clear sky", "12$DEGREE"),
            FakeData(date = "2022-10-26", time = "15:00", R.drawable.preview_all_cloud, des = "few clouds","15$DEGREE"),
            FakeData(date = "2022-10-26", time = "18:00", R.drawable.preview_all_cloud, des = "broken clouds","19$DEGREE"),
            FakeData(date = "2022-10-26", time = "21:00", R.drawable.preview_all_cloud, des = "broken clouds","22$DEGREE"),
            FakeData(date = "2022-10-27", time = "00:00", R.drawable.preview_all_cloud, des = "few clouds","15$DEGREE"),
            FakeData(date = "2022-10-26", time = "12:00", R.drawable.preview_all_cloud, des = "clear sky", "12$DEGREE"),
            FakeData(date = "2022-10-26", time = "15:00", R.drawable.preview_all_cloud, des = "few clouds","15$DEGREE"),
            FakeData(date = "2022-10-26", time = "18:00", R.drawable.preview_all_cloud, des = "broken clouds","19$DEGREE"),
            FakeData(date = "2022-10-26", time = "21:00", R.drawable.preview_all_cloud, des = "broken clouds","22$DEGREE"),
            FakeData(date = "2022-10-27", time = "00:00", R.drawable.preview_all_cloud, des = "few clouds","15$DEGREE"),
            FakeData(date = "2022-10-26", time = "12:00", R.drawable.preview_all_cloud, des = "clear sky", "12$DEGREE"),
            FakeData(date = "2022-10-26", time = "15:00", R.drawable.preview_all_cloud, des = "few clouds","15$DEGREE"),
            FakeData(date = "2022-10-26", time = "18:00", R.drawable.preview_all_cloud, des = "broken clouds","19$DEGREE"),
            FakeData(date = "2022-10-26", time = "21:00", R.drawable.preview_all_cloud, des = "broken clouds","22$DEGREE"),
            FakeData(date = "2022-10-27", time = "00:00", R.drawable.preview_all_cloud, des = "few clouds","15$DEGREE"),
            FakeData(date = "2022-10-26", time = "12:00", R.drawable.preview_all_cloud, des = "clear sky", "12$DEGREE"),
            FakeData(date = "2022-10-26", time = "15:00", R.drawable.preview_all_cloud, des = "few clouds","15$DEGREE"),
            FakeData(date = "2022-10-26", time = "18:00", R.drawable.preview_all_cloud, des = "broken clouds","19$DEGREE"),
            FakeData(date = "2022-10-26", time = "21:00", R.drawable.preview_all_cloud, des = "broken clouds","22$DEGREE"),
            FakeData(date = "2022-10-27", time = "00:00", R.drawable.preview_all_cloud, des = "few clouds","15$DEGREE"),
            FakeData(date = "2022-10-26", time = "12:00", R.drawable.preview_all_cloud, des = "clear sky", "12$DEGREE"),
            FakeData(date = "2022-10-26", time = "15:00", R.drawable.preview_all_cloud, des = "few clouds","15$DEGREE"),
            FakeData(date = "2022-10-26", time = "18:00", R.drawable.preview_all_cloud, des = "broken clouds","19$DEGREE"),
            FakeData(date = "2022-10-26", time = "21:00", R.drawable.preview_all_cloud, des = "broken clouds","22$DEGREE"),
            FakeData(date = "2022-10-27", time = "00:00", R.drawable.preview_all_cloud, des = "few clouds","15$DEGREE"),
            FakeData(date = "2022-10-26", time = "12:00", R.drawable.preview_all_cloud, des = "clear sky", "12$DEGREE"),
            FakeData(date = "2022-10-26", time = "15:00", R.drawable.preview_all_cloud, des = "few clouds","15$DEGREE"),
            FakeData(date = "2022-10-26", time = "18:00", R.drawable.preview_all_cloud, des = "broken clouds","19$DEGREE"),
            FakeData(date = "2022-10-26", time = "21:00", R.drawable.preview_all_cloud, des = "broken clouds","22$DEGREE"),
            FakeData(date = "2022-10-27", time = "00:00", R.drawable.preview_all_cloud, des = "few clouds","15$DEGREE"),
        )
    }
}