package com.thanaa.restaurantweatherapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thanaa.restaurantweatherapp.model.Plan
import com.thanaa.restaurantweatherapp.repository.PlanRepository
import kotlinx.coroutines.launch

class PlanViewModel(val repository: PlanRepository) : ViewModel() {

    val getAllData: LiveData<List<Plan>> = repository.getAllData
    val sortByNewDate: LiveData<List<Plan>> = repository.sortByNewDate
    val sortByOldDate: LiveData<List<Plan>> = repository.sortByOldDate

    val emptyDatabase: MutableLiveData<Boolean> = MutableLiveData(true)

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

