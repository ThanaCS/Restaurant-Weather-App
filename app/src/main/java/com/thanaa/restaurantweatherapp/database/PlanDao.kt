package com.thanaa.restaurantweatherapp.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.thanaa.restaurantweatherapp.model.Plan

@Dao
interface PlanDao {
    @Query("SELECT * FROM plan_table ORDER BY id ASC")
    fun getAllData(): LiveData<List<Plan>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertPlan(plan: Plan)

    @Update
    suspend fun updatePlan(plan: Plan)

    @Delete
    suspend fun deleteItem(plan: Plan)

    @Query("DELETE FROM plan_table")
    suspend fun deleteAll()

    @Query("SELECT * FROM plan_table ORDER BY date DESC")
    fun sortByNewDate(): LiveData<List<Plan>>

    @Query("SELECT * FROM  plan_table ORDER BY date ASC")
    fun sortByOldDate(): LiveData<List<Plan>>

    @Query("SELECT * FROM plan_table ORDER BY title ASC")
    fun sortByTitle(): LiveData<List<Plan>>

    @Query("SELECT * FROM plan_table WHERE title LIKE :searchQuery")
    fun searchTitle(searchQuery: String): LiveData<List<Plan>>


}
