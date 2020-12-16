package com.thanaa.restaurantweatherapp.viewmodel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thanaa.restaurantweatherapp.RestaurantModel.RestaurantResponse
import com.thanaa.restaurantweatherapp.api.YelpService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class YelpViewModel: ViewModel() {

    val businessesLiveData: MutableLiveData<RestaurantResponse> = MutableLiveData()
    private val yelpService = YelpService()


    fun getBusinesses(term: String, location: String) {
        try {
            viewModelScope.launch(Dispatchers.IO) {
                val response = yelpService.getBusinesses(term, location)
                if (response.isSuccessful)
                    businessesLiveData.postValue(response.body())
            }
        } catch (exception: Exception) {
            exception.printStackTrace()
        }


    }

}