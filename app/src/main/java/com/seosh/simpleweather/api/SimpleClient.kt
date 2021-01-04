package com.seosh.simpleweather.api

import com.seosh.simpleweather.api.data.WeatherResponse

class SimpleClient {
    private val retrofit = SimpleRetrofit().create()

    fun requestWeather(city: String, callback: SimpleCallback<WeatherResponse>) {
        retrofit.requestWeather(city = city).enqueue(callback)
    }
}