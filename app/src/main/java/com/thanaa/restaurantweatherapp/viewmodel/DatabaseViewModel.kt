package com.thanaa.restaurantweatherapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.thanaa.restaurantweatherapp.database.BusinessDatabase
import com.thanaa.restaurantweatherapp.model.Businesses
import com.thanaa.restaurantweatherapp.repository.DatabaseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DatabaseViewModel(application: Application) : AndroidViewModel(application) {

    private val businessDao = BusinessDatabase.getDatabase(application).BusinessDao()
    private val repository: DatabaseRepository
    val getAllData: LiveData<List<Businesses>>


    init {
        repository = DatabaseRepository(businessDao)
        getAllData = repository.getAllData
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

}