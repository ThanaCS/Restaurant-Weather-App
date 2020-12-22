package com.thanaa.restaurantweatherapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thanaa.restaurantweatherapp.model.Businesses
import com.thanaa.restaurantweatherapp.repository.Repository
import kotlinx.coroutines.launch

class YelpViewModel : ViewModel() {
    private val repository = Repository()
    val businessesLiveData: MutableLiveData<List<Businesses>> = MutableLiveData()
    var emptyBusinesses: MutableLiveData<Boolean> = MutableLiveData(false)



    fun getBusinesses(term: String, location: String) {
        try {
            viewModelScope.launch {
                val response = repository.getBusinesses(term, location)
                if (response.isSuccessful) {
                    businessesLiveData.postValue(response.body()?.businesses)
                } else {
                    emptyBusinesses.postValue(true)
                }


            }
        } catch (exception: Exception) {
            exception.printStackTrace()
        }

    }


}