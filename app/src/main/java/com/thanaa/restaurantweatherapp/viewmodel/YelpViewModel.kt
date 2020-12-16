package com.thanaa.restaurantweatherapp.viewmodel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.thanaa.restaurantweatherapp.RestaurantModel.RestaurantResponse
import com.thanaa.restaurantweatherapp.api.YelpService

class YelpViewModel: ViewModel() {

     val businessesLiveData: MutableLiveData<RestaurantResponse> = MutableLiveData()
     val yelpService = YelpService()


//    fun getBusinesses(term: String, location: String) {
//        try {
//            viewModelScope.launch {
//                val response = yelpService.getBusinesses(term, location)
//                if (response.isSuccessful)
//                    businessesLiveData.postValue(response.body())
//            }
//        } catch (exception: Exception) {
//            exception.printStackTrace()
//        }
//
//
//    }
}