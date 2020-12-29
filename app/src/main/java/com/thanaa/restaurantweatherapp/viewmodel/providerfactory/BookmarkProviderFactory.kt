package com.thanaa.restaurantweatherapp.viewmodel.providerfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.thanaa.restaurantweatherapp.repository.BookmarkRepository
import com.thanaa.restaurantweatherapp.viewmodel.BookmarkViewModel

class BookmarkProviderFactory(val repository: BookmarkRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return BookmarkViewModel(repository) as T
    }

}