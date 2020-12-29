package com.thanaa.restaurantweatherapp.repository

import androidx.lifecycle.LiveData
import com.thanaa.restaurantweatherapp.database.AppDatabase
import com.thanaa.restaurantweatherapp.model.Plan

class PlanRepository(private val database: AppDatabase) {

    val getAllData: LiveData<List<Plan>> = database.PlanDao().getAllData()
    val sortByNewDate: LiveData<List<Plan>> = database.PlanDao().sortByNewDate()
    val sortByOldDate: LiveData<List<Plan>> = database.PlanDao().sortByOldDate()

    suspend fun insertData(plan: Plan) {
        database.PlanDao().insertPlan(plan)
    }

    fun searchTitle(searchQuery: String): LiveData<List<Plan>> {
        return database.PlanDao().searchTitle(searchQuery)
    }

    suspend fun updateData(plan: Plan) {
        database.PlanDao().updatePlan(plan)
    }

    suspend fun deleteItem(plan: Plan) {
        database.PlanDao().deleteItem(plan)
    }

    suspend fun deleteAll() {
        database.PlanDao().deleteAll()
    }

}