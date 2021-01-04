package com.seosh.simpleweather.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.seosh.simpleweather.R
import com.seosh.simpleweather.repository.data.CityItem

class CityAdapterViewModel : ViewModel() {

    val onSelectedItem = MutableLiveData<CityItem>()
    val onSavedItem = MutableLiveData<CityItem>()

    fun onItemSelected(item: CityItem) {
        onSelectedItem.value = item
    }

    fun onItemSaved(item: CityItem?) {
        onSavedItem.value = item
    }

    fun getRes(item: CityItem) : Int {
        return when(item.isFavorite) {
            true -> R.drawable.ic_favorite
            false -> R.drawable.ic_non_favorite
        }
    }
}