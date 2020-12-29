package com.thanaa.restaurantweatherapp.viewmodel.providerfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.thanaa.restaurantweatherapp.repository.HistoryRepository
import com.thanaa.restaurantweatherapp.viewmodel.HistoryViewModel


class HistoryProviderFactory(val repository: HistoryRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return HistoryViewModel(repository) as T
    }

}