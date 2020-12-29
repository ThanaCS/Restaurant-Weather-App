package com.thanaa.restaurantweatherapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thanaa.restaurantweatherapp.model.Businesses
import com.thanaa.restaurantweatherapp.repository.HistoryRepository
import kotlinx.coroutines.launch

class HistoryViewModel(val repository: HistoryRepository) : ViewModel() {

    val emptyDatabase: MutableLiveData<Boolean> = MutableLiveData(true)
    val getAllData: LiveData<List<Businesses>> = repository.getAllData
    val sortByPrice: LiveData<List<Businesses>> = repository.sortByPrice

    fun insertBusiness(businesses: Businesses) {
        viewModelScope.launch {
            repository.insertData(businesses)
        }
    }

    fun deleteAll() {
        viewModelScope.launch {
            repository.deleteAll()
        }
    }


    fun checkIfDatabaseEmpty(businesses: List<Businesses>) {
        emptyDatabase.value = businesses.isEmpty()
    }

}