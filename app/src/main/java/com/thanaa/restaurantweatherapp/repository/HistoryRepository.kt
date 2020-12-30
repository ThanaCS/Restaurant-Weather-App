package com.thanaa.restaurantweatherapp.repository

import androidx.lifecycle.LiveData
import com.thanaa.restaurantweatherapp.database.AppDatabase
import com.thanaa.restaurantweatherapp.model.Businesses

class HistoryRepository(private val database: AppDatabase) {

    val getAllData: LiveData<List<Businesses>> = database.BusinessDao().getBusiness()
    val sortByPrice: LiveData<List<Businesses>> = database.BusinessDao().sortByPrice()
    suspend fun insertData(businesses: Businesses) {
        database.BusinessDao().insertBusiness(businesses)
    }

    suspend fun deleteAll() {
        database.BusinessDao().deleteAll()
    }

}