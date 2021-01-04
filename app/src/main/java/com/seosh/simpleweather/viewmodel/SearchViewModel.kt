package com.seosh.simpleweather.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seosh.simpleweather.repository.AssetRepository
import com.seosh.simpleweather.repository.DatabaseRepository
import com.seosh.simpleweather.repository.data.CityItem
import kotlinx.coroutines.launch

class SearchViewModel(private val assetRepository: AssetRepository,
                      private val databaseRepository: DatabaseRepository) : ViewModel() {

    companion object {
        private const val TAG = "[SearchViewModel]"
    }

    val resultCityList = assetRepository.resultCityList
    val resultFavorites = databaseRepository.resultFavorites

    fun requestCityList() {
        assetRepository.requestCityList()
    }

    fun getFavorites() {
        viewModelScope.launch {
            databaseRepository.getAllFavorites()
        }
    }

    fun insertFavorite(item: CityItem) {
        viewModelScope.launch {
            // Log.d("seosh", "${TAG} [insertFavorite] insert item to DB (${item})" )
            databaseRepository.insertFavorite(item)
        }
    }

    fun deleteFavorite(item: CityItem) {
        viewModelScope.launch {
            databaseRepository.deleteFavorite(item)
        }
    }
}