package com.thanaa.restaurantweatherapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.thanaa.restaurantweatherapp.model.Plan

class SharedViewModel : ViewModel() {

    val emptyDatabase: MutableLiveData<Boolean> = MutableLiveData(true)
    fun checkIfDatabaseEmpty(plan: List<Plan>) {
        emptyDatabase.value = plan.isEmpty()
    }

}