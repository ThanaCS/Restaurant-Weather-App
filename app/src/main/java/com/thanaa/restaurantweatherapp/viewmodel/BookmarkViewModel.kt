package com.thanaa.restaurantweatherapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thanaa.restaurantweatherapp.model.Bookmark
import com.thanaa.restaurantweatherapp.repository.BookmarkRepository
import kotlinx.coroutines.launch

class BookmarkViewModel(val repository: BookmarkRepository) : ViewModel() {

    val getAllData: LiveData<List<Bookmark>> = repository.getAllData

    val emptyDatabase: MutableLiveData<Boolean> = MutableLiveData(true)

    fun insertData(bookmark: Bookmark) {
        viewModelScope.launch {
            repository.insertData(bookmark)
        }
    }

    fun deleteItem(bookmark: Bookmark) {
        viewModelScope.launch {
            repository.deleteItem(bookmark)
        }
    }

    fun updateData(bookmark: Bookmark) {
        viewModelScope.launch {
            repository.updateData(bookmark)
        }
    }

    fun deleteAll() {
        viewModelScope.launch {
            repository.deleteAll()
        }
    }


    fun checkIfDatabaseEmpty(bookmark: List<Bookmark>) {
        emptyDatabase.value = bookmark.isEmpty()
    }

}