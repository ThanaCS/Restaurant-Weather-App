package com.thanaa.restaurantweatherapp.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.thanaa.restaurantweatherapp.model.Businesses

@Dao
interface BusinessDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBusiness(businesses: Businesses)

    @Query("SELECT * FROM businesses_table ORDER BY id ASC")
    fun getBusiness(): LiveData<List<Businesses>>

    @Delete
    suspend fun deleteBusiness(businesses: Businesses)


}