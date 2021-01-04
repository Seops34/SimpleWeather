package com.seosh.simpleweather.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.seosh.simpleweather.repository.data.CityPosition
import java.io.Serializable

@Entity(tableName = "favorite")
data class FavoriteCity(
    @PrimaryKey var id: Int,
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "country") var country: String,
    @ColumnInfo(name = "lat") var lat: Double,
    @ColumnInfo(name = "lon") var lon: Double
)