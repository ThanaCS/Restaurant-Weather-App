package com.thanaa.restaurantweatherapp.viewmodel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thanaa.restaurantweatherapp.api.YelpService
import kotlinx.coroutines.launch

class YelpViewModel: ViewModel() {

    private val businessesLiveData: MutableLiveData<List<Any>> = MutableLiveData()
    private val yelpService = YelpService()

    // get photos near current location
    fun getBusinesses(term: String, location: String) {
        try {
            viewModelScope.launch {
                val response = yelpService.getBusinesses(term, location)
                if (response.isSuccessful)
                    businessesLiveData.postValue(response.body())
            }
        } catch (exception: Exception) {
            exception.printStackTrace()
        }


    }
}