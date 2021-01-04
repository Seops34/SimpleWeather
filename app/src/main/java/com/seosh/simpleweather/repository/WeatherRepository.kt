package com.seosh.simpleweather.repository

import androidx.lifecycle.MutableLiveData
import com.seosh.simpleweather.api.SimpleCallback
import com.seosh.simpleweather.api.SimpleClient
import com.seosh.simpleweather.api.data.SimpleResult
import com.seosh.simpleweather.api.data.Status
import com.seosh.simpleweather.api.data.WeatherResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class WeatherRepository {
    private val client = SimpleClient()

    var resultWeather = MutableLiveData<SimpleResult<WeatherResponse>>()

    suspend fun requestWeather(city: String) = withContext(Dispatchers.IO) {
        client.requestWeather(city, object : SimpleCallback<WeatherResponse>() {
            override fun onSuccess(result: WeatherResponse?) {
                resultWeather.value = SimpleResult<WeatherResponse>(Status.SUCCESS, result)
            }

            override fun onFail(code: Int, result: String) {
                resultWeather.value = when(code) {
                    0 -> SimpleResult<WeatherResponse>(Status.ERROR, result)
                    else -> SimpleResult<WeatherResponse>(Status.FAIL, result)
                }
            }
        })
    }
}