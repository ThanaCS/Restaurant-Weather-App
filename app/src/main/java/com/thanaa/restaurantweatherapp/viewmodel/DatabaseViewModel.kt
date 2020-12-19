package com.thanaa.restaurantweatherapp.viewmodel

import androidx.lifecycle.ViewModel
import com.thanaa.restaurantweatherapp.repository.DatabaseRepository

class DatabaseViewModel(val newRepository: DatabaseRepository) : ViewModel()