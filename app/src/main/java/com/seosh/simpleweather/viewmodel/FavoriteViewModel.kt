package com.seosh.simpleweather.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seosh.simpleweather.repository.DatabaseRepository
import com.seosh.simpleweather.repository.data.CityItem
import kotlinx.coroutines.launch

class FavoriteViewModel(private val databaseRepository: DatabaseRepository) : ViewModel() {

    val resultFavorites = databaseRepository.resultFavorites

    fun getFavorites() {
        viewModelScope.launch {
            databaseRepository.getAllFavorites()
        }
    }

    fun deleteFavorite(item: CityItem) {
        viewModelScope.launch {
            databaseRepository.deleteFavorite(item)
        }
    }
}