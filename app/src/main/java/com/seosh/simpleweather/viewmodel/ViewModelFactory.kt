package com.seosh.simpleweather.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.seosh.simpleweather.repository.AssetRepository
import com.seosh.simpleweather.repository.DatabaseRepository
import com.seosh.simpleweather.repository.WeatherRepository
import java.lang.IllegalArgumentException

class SearchViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if(modelClass.isAssignableFrom(SearchViewModel::class.java)) {
            SearchViewModel(AssetRepository(context), DatabaseRepository(context)) as T
        } else {
            throw IllegalArgumentException()
        }
    }
}

class CityAdapterViewModelFactory() : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if(modelClass.isAssignableFrom(CityAdapterViewModel::class.java)) {
            CityAdapterViewModel() as T
        } else {
            throw IllegalArgumentException()
        }
    }
}

class DetailViewModelFactory() : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if(modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            DetailViewModel(WeatherRepository()) as T
        } else {
            throw IllegalArgumentException()
        }
    }
}

class FavoriteViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if(modelClass.isAssignableFrom(FavoriteViewModel::class.java)) {
            FavoriteViewModel(DatabaseRepository(context)) as T
        } else {
            throw IllegalArgumentException()
        }
    }
}