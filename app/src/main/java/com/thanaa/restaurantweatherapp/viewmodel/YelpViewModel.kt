package com.thanaa.restaurantweatherapp.viewmodel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thanaa.restaurantweatherapp.RestaurantModel.Businesses
import com.thanaa.restaurantweatherapp.api.YelpService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class YelpViewModel: ViewModel() {

    val businessesLiveData: MutableLiveData<List<Businesses>> = MutableLiveData()
    val businessesLanLonLiveData: MutableLiveData<List<Businesses>> = MutableLiveData()
    private val yelpService = YelpService()


    fun getBusinesses(term: String, location: String) {
        try {
            viewModelScope.launch(Dispatchers.IO) {
                val response = yelpService.getBusinesses(term, location)
                if (response.isSuccessful)
                    businessesLiveData.postValue(response.body()?.businesses)
            }
        } catch (exception: Exception) {
            exception.printStackTrace()
        }


    }

    fun getBusinessesFromLanLon(term: String, lat: String, lon: String) {
        try {
            viewModelScope.launch {
                val response = yelpService.getBusinessesFromLanLon(term, lat, lon)
                if (response.isSuccessful)
                    businessesLanLonLiveData.postValue(response.body()?.businesses)
            }
        } catch (exception: Exception) {
            exception.printStackTrace()
        }


    }


}