package com.seosh.simpleweather.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seosh.simpleweather.repository.WeatherRepository
import com.seosh.simpleweather.utils.Constants
import kotlinx.coroutines.launch

class DetailViewModel(private val weatherRepository: WeatherRepository) : ViewModel() {

    val resultWeather = weatherRepository.resultWeather

    fun requestWeather(city: String) {
        viewModelScope.launch {
            weatherRepository.requestWeather(city = city)
        }
    }

    fun getTemperature(temp: Float?) = "${temp.toString()}°C"

    fun getImageUrl(image: String?) = "${Constants.URL_WEATHER_ICON}/img/wn/${image}@2x.png"
}