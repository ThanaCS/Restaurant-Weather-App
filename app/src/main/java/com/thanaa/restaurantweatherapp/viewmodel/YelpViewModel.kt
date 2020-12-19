package com.thanaa.restaurantweatherapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thanaa.restaurantweatherapp.model.Businesses
import com.thanaa.restaurantweatherapp.repository.Repository
import kotlinx.coroutines.launch

class YelpViewModel : ViewModel() {

    val businessesLiveData: MutableLiveData<List<Businesses>> = MutableLiveData()
    val businessesLanLonLiveData: MutableLiveData<List<Businesses>> = MutableLiveData()
    private val repository = Repository()


    fun getBusinessesFromLanLon(term: String, lat: String, lon: String) {
        try {
            viewModelScope.launch {
                val response = repository.getBusinessesFromLanLon(term, lat, lon)
                if (response.isSuccessful)
                    businessesLanLonLiveData.postValue(response.body()?.businesses)
            }
        } catch (exception: Exception) {
            exception.printStackTrace()
        }


    }

    fun getBusinesses(term: String, location: String) {
        try {
            viewModelScope.launch {
                val response = repository.getBusinesses(term, location)
                if (response.isSuccessful)
                    businessesLiveData.postValue(response.body()?.businesses)
            }
        } catch (exception: Exception) {
            exception.printStackTrace()
        }

    }


}