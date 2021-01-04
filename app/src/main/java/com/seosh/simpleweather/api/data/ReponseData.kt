package com.seosh.simpleweather.api.data

import com.google.gson.annotations.SerializedName

data class WeatherResponse(
    @SerializedName("weather") val weather: ArrayList<Weather>,
    @SerializedName("main") val climate: Climate
)

data class Climate(
    @SerializedName("temp") val temperature: Float,
    @SerializedName("feels_like") val feelTemp: Float,
    @SerializedName("temp_min") val minTemp: Float,
    @SerializedName("temp_max") val maxTemp: Float,
    @SerializedName("pressure") val pressure: Float,
    @SerializedName("humidity") val humidity: Float
)

data class Weather(
    @SerializedName("id") val id: Int,
    @SerializedName("main") val main: String,
    @SerializedName("description") val desc: String,
    @SerializedName("icon") val icon: String
)