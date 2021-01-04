package com.seosh.simpleweather.repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.seosh.simpleweather.repository.data.CityItem

class AssetRepository(context: Context) {
    companion object {
        private const val TAG = "[AssetRepository]"
        private const val ASSET_CITY_LIST = "citylist.json"
    }

    private val assetManager = context.resources.assets
    private val gson = Gson()

    var resultCityList = MutableLiveData<List<CityItem>>()

    fun requestCityList() {
        val inputStream = assetManager.open(ASSET_CITY_LIST)
        val stringCityList = inputStream.bufferedReader().use {
            it.readText()
        }

        val cityList = gson.fromJson(stringCityList, Array<CityItem>::class.java).toList()
        
        cityList.forEach { city ->
//            Log.d("seosh", "${TAG} [requestCityList] item : ${city}")
            city.isFavorite = false
            city.isShown = true
        }

        val filteredList = getFilteredList(cityList)

        resultCityList.value = filteredList
    }

    private fun getFilteredList(list : List<CityItem>) : List<CityItem> {
        return list.filter { item ->
            (!item.name.equals("-") && !item.name.isBlank())
        }.sortedBy { item ->
            item.name
        }
    }
}