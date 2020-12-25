package com.thanaa.restaurantweatherapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.thanaa.restaurantweatherapp.database.AppDatabase
import com.thanaa.restaurantweatherapp.model.Businesses
import com.thanaa.restaurantweatherapp.repository.BusinessRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BusinessViewModel(application: Application) : AndroidViewModel(application) {

    private val businessDao = AppDatabase.getDatabase(application).BusinessDao()
    private val repository: BusinessRepository
    val emptyDatabase: MutableLiveData<Boolean> = MutableLiveData(true)
    val getAllData: LiveData<List<Businesses>>
    val sortByPrice: LiveData<List<Businesses>>

    init {
        repository = BusinessRepository(businessDao)
        getAllData = repository.getAllData
        sortByPrice = repository.sortByPrice
    }

    fun insertBusiness(businesses: Businesses) {
        viewModelScope.launch {
            repository.insertData(businesses)
        }
    }

    fun deleteAll() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAll()
        }
    }


    fun checkIfDatabaseEmpty(businesses: List<Businesses>) {
        emptyDatabase.value = businesses.isEmpty()
    }

}