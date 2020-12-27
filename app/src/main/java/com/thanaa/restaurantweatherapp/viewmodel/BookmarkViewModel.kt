package com.thanaa.restaurantweatherapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel

class BookmarkViewModel(application: Application) : AndroidViewModel(application) {
//    private val bookmarkDao = AppDatabase.getDatabase(application).BookmarkDao()
//    private val repository: BookmarkRepository
//    val getAllData: LiveData<List<Plan>>
//
//    val emptyDatabase: MutableLiveData<Boolean> = MutableLiveData(true)
//
//    init {
//        repository = BookmarkRepository(bookmarkDao)
//        getAllData = repository.getAllData
//
//    }
//
//    fun insertData(plan: Plan) {
//        viewModelScope.launch {
//            repository.insertData(plan)
//        }
//    }
//
//    fun deleteItem(plan: Plan) {
//        viewModelScope.launch {
//            repository.deleteItem(plan)
//        }
//    }
//
//    fun updateData(plan: Plan) {
//        viewModelScope.launch {
//            repository.updateData(plan)
//        }
//    }
//
//    fun deleteAll() {
//        viewModelScope.launch {
//            repository.deleteAll()
//        }
//    }
//
//    fun searchTitle(searchQuery: String): LiveData<List<Plan>> {
//        return repository.searchTitle(searchQuery)
//    }
//
//    fun checkIfDatabaseEmpty(plan: List<Plan>) {
//        emptyDatabase.value = plan.isEmpty()
//    }

}