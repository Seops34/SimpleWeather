package com.seosh.simpleweather.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query

@Dao
interface CityDao {
    @Query("SELECT * FROM favorite")
    fun getAll(): List<FavoriteCity>

    @Insert(onConflict = REPLACE)
    fun insert(city: FavoriteCity)

    @Delete
    fun delete(city: FavoriteCity)
}