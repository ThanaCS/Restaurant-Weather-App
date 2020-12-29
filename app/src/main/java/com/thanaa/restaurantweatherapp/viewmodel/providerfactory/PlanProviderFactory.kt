package com.thanaa.restaurantweatherapp.viewmodel.providerfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.thanaa.restaurantweatherapp.repository.PlanRepository
import com.thanaa.restaurantweatherapp.viewmodel.PlanViewModel

class PlanProviderFactory(val repository: PlanRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return PlanViewModel(repository) as T
    }

}