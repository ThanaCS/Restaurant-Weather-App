package com.thanaa.restaurantweatherapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thanaa.restaurantweatherapp.repository.ApiRepository
import com.thanaa.restaurantweatherapp.weatherModel.WeatherResponse
import kotlinx.coroutines.launch

class WeatherViewModel : ViewModel() {
    val weatherLiveData: MutableLiveData<WeatherResponse> = MutableLiveData()
    private val repository = ApiRepository()

    fun getWeather(latlon: String) {
        try {
            viewModelScope.launch {
                val response = repository.getWeather(latlon)
                if (response.isSuccessful)
                    weatherLiveData.postValue(response.body())
            }
        } catch (exception: Exception) {
            exception.printStackTrace()
        }

    }
}