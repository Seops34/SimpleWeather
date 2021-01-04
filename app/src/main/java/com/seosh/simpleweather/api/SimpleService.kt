package com.seosh.simpleweather.api

import com.seosh.simpleweather.BuildConfig
import com.seosh.simpleweather.api.data.WeatherResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface SimpleService {
    @GET("data/2.5/weather")
    fun requestWeather(
        @Query("q") city: String,
        @Query("units") units: String = "metric",
        @Query("appid") apiKey: String = BuildConfig.OPEN_WEATHER_MAP_KEY
    ) : Call<WeatherResponse>
}