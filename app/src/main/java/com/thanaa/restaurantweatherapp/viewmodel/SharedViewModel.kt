package com.thanaa.restaurantweatherapp.viewmodel

import android.text.TextUtils
import androidx.lifecycle.MutableLiveData
import com.thanaa.restaurantweatherapp.model.Plan

class SharedViewModel {

    val emptyDatabase: MutableLiveData<Boolean> = MutableLiveData(true)
    fun checkIfDatabaseEmpty(plan: List<Plan>) {
        emptyDatabase.value = plan.isEmpty()
    }

    fun verifyDataFormUser(title: String, description: String): Boolean {
        return !(TextUtils.isEmpty(title) || TextUtils.isEmpty(description))
    }
}