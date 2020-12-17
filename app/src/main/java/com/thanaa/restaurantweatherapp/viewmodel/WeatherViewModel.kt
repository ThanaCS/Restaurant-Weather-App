package com.thanaa.restaurantweatherapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thanaa.restaurantweatherapp.api.WeatherService
import com.thanaa.restaurantweatherapp.weatherModel.WeatherResponse
import kotlinx.coroutines.launch

class WeatherViewModel : ViewModel() {
    val weatherLiveData: MutableLiveData<WeatherResponse> = MutableLiveData()
    private val weatherService = WeatherService()

    fun getWeather(latlon: String) {
        try {
            viewModelScope.launch {
                val response = weatherService.getWeather(latlon)
                if (response.isSuccessful)
                    weatherLiveData.postValue(response.body())
            }
        } catch (exception: Exception) {
            exception.printStackTrace()
        }

    }
}