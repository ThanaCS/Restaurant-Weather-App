package com.thanaa.restaurantweatherapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.thanaa.restaurantweatherapp.R
import com.thanaa.restaurantweatherapp.database.BusinessDatabase
import com.thanaa.restaurantweatherapp.repository.DatabaseRepository
import com.thanaa.restaurantweatherapp.viewmodel.DatabaseViewModel
import com.thanaa.restaurantweatherapp.viewmodel.ViewModelFactory


class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"
    lateinit var viewModel: DatabaseViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)


        bottomNavigationView.background = null
        val navController: NavController =
            Navigation.findNavController(this, R.id.fragment_container)
        NavigationUI.setupWithNavController(bottomNavigationView, navController)

        val newRepository = DatabaseRepository(BusinessDatabase(this))
        val viewModelProviderFactory = ViewModelFactory(newRepository)
        viewModel = ViewModelProvider(this, viewModelProviderFactory).get(
            DatabaseViewModel::class.java
        )
        findNavController(R.id.fragment_container)
    }
}