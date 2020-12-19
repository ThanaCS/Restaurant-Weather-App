package com.thanaa.restaurantweatherapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.thanaa.restaurantweatherapp.repository.DatabaseRepository

class ViewModelFactory(private val newRepository: DatabaseRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return DatabaseViewModel(newRepository) as T
    }
}