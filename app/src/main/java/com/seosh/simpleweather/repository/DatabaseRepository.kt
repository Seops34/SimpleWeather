package com.seosh.simpleweather.repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.seosh.simpleweather.database.FavoriteCity
import com.seosh.simpleweather.database.FavoriteDatabase
import com.seosh.simpleweather.repository.data.CityItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DatabaseRepository(private val context: Context) {
    companion object {
        private const val TAG = "[DatabaseRepository]"
    }

    private val favoriteDatabase = FavoriteDatabase.getInstance(context)

    val resultFavorites = MutableLiveData<List<FavoriteCity>>()

    suspend fun getAllFavorites() = withContext(Dispatchers.IO) {
        resultFavorites.postValue(favoriteDatabase?.cityDao()?.getAll())
        // Log.d("seosh", "${TAG} [getAllFavorites] get all item")
    }

    suspend fun insertFavorite(city: CityItem) = withContext(Dispatchers.IO) {
        val favoriteCity = city.run {
            FavoriteCity(id, name, country, coord.lat, coord.lon)
        }

        favoriteDatabase?.cityDao()?.insert(favoriteCity)
        // Log.d("seosh", "${TAG} [insertFavorite] insert item")
    }

    suspend fun deleteFavorite(city: CityItem) = withContext(Dispatchers.IO) {
        val favoriteCity = city.run {
            FavoriteCity(id, name, country, coord.lat, coord.lon)
        }

        favoriteDatabase?.cityDao()?.delete(favoriteCity)
        // Log.d("seosh", "${TAG} [deleteFavorite] delete item")
    }}