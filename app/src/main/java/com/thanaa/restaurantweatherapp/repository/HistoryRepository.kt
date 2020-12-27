package com.thanaa.restaurantweatherapp.repository

import androidx.lifecycle.LiveData
import com.thanaa.restaurantweatherapp.database.BusinessDao
import com.thanaa.restaurantweatherapp.model.Businesses

class HistoryRepository(private val businessDao: BusinessDao) {

    val getAllData: LiveData<List<Businesses>> = businessDao.getBusiness()
    val sortByPrice: LiveData<List<Businesses>> = businessDao.sortByHighestPrice()
    suspend fun insertData(businesses: Businesses) {
        businessDao.insertBusiness(businesses)
    }

    suspend fun deleteAll() {
        businessDao.deleteAll()
    }


}