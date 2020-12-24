package com.thanaa.restaurantweatherapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.thanaa.restaurantweatherapp.database.AppDatabase
import com.thanaa.restaurantweatherapp.model.Plan
import com.thanaa.restaurantweatherapp.repository.PlanRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PlanViewModel(application: Application) : AndroidViewModel(application) {
    private val todoDao = AppDatabase.getDatabase(application).PlanDao()
    private val repository: PlanRepository
    val getAllData: LiveData<List<Plan>>
    val sortByNewDate: LiveData<List<Plan>>
    val sortByOldDate: LiveData<List<Plan>>


    init {
        repository = PlanRepository(todoDao)
        getAllData = repository.getAllData
        sortByNewDate = repository.sortByNewDate
        sortByOldDate = repository.sortByOldDate
    }

    fun insertData(plan: Plan) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertData(plan)
        }
    }

    fun deleteItem(plan: Plan) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteItem(plan)
        }
    }

    fun updateData(plan: Plan) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateData(plan)
        }
    }

    fun deleteAll() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAll()
        }
    }

    fun searchTitle(searchQuery: String): LiveData<List<Plan>> {
        return repository.searchTitle(searchQuery)
    }

}