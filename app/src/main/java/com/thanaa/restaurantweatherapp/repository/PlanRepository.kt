package com.thanaa.restaurantweatherapp.repository

import androidx.lifecycle.LiveData
import com.thanaa.restaurantweatherapp.database.PlanDao
import com.thanaa.restaurantweatherapp.model.Plan

class PlanRepository(private val planDao: PlanDao) {
    val getAllData: LiveData<List<Plan>> = planDao.getAllData()
    val sortByNewDate: LiveData<List<Plan>> = planDao.sortByNewDate()
    val sortByOldDate: LiveData<List<Plan>> = planDao.sortByOldDate()

    suspend fun insertData(plan: Plan) {
        planDao.insertPlan(plan)
    }

    fun searchTitle(searchQuery: String): LiveData<List<Plan>> {
        return planDao.searchTitle(searchQuery)
    }

    suspend fun updateData(plan: Plan) {
        planDao.updatePlan(plan)
    }

    suspend fun deleteItem(plan: Plan) {
        planDao.deleteItem(plan)
    }

    suspend fun deleteAll() {
        planDao.deleteAll()
    }

}