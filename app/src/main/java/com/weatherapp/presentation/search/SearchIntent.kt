package com.weatherapp.presentation.search


sealed class SearchIntent()  {
    data class Search(val keyword: String) : SearchIntent()
    data class UpdateSearchKeyword(val keyword: String) : SearchIntent()
}
