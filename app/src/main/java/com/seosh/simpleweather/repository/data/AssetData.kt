package com.seosh.simpleweather.repository.data

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class CityItem(
    @SerializedName("id") val id : Int,
    @SerializedName("name") val name: String,
    @SerializedName("country") val country: String,
    @SerializedName("coord") val coord: CityPosition,
    var isFavorite: Boolean = false,
    var isShown: Boolean = false
) : Serializable

data class CityPosition(
    @SerializedName("lon") val lon: Double,
    @SerializedName("lat") val lat: Double
) : Serializable