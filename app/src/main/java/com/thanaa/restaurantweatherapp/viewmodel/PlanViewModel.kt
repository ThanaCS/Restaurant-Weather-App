package com.thanaa.restaurantweatherapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.thanaa.restaurantweatherapp.database.AppDatabase
import com.thanaa.restaurantweatherapp.model.Plan
import com.thanaa.restaurantweatherapp.repository.PlanRepository
import kotlinx.coroutines.launch

class PlanViewModel(application: Application) : AndroidViewModel(application) {
    private val planDao = AppDatabase.getDatabase(application).PlanDao()
    private val repository: PlanRepository
    val getAllData: LiveData<List<Plan>>
    val sortByNewDate: LiveData<List<Plan>>
    val sortByOldDate: LiveData<List<Plan>>
    val emptyDatabase: MutableLiveData<Boolean> = MutableLiveData(true)

    init {
        repository = PlanRepository(planDao)
        getAllData = repository.getAllData
        sortByNewDate = repository.sortByNewDate
        sortByOldDate = repository.sortByOldDate
    }

    fun insertData(plan: Plan) {
        viewModelScope.launch {
            repository.insertData(plan)
        }
    }

    fun deleteItem(plan: Plan) {
        viewModelScope.launch {
            repository.deleteItem(plan)
        }
    }

    fun updateData(plan: Plan) {
        viewModelScope.launch {
            repository.updateData(plan)
        }
    }

    fun deleteAll() {
        viewModelScope.launch {
            repository.deleteAll()
        }
    }

    fun searchTitle(searchQuery: String): LiveData<List<Plan>> {
        return repository.searchTitle(searchQuery)
    }

    fun checkIfDatabaseEmpty(plan: List<Plan>) {
        emptyDatabase.value = plan.isEmpty()
    }

}