package com.thanaa.restaurantweatherapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.thanaa.restaurantweatherapp.R


class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)


        bottomNavigationView.background = null
        val navController: NavController =
            Navigation.findNavController(this, R.id.fragment_container)
        NavigationUI.setupWithNavController(bottomNavigationView, navController)

        findNavController(R.id.fragment_container)
    }
}