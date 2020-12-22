package com.thanaa.restaurantweatherapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.thanaa.restaurantweatherapp.R


class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"
    lateinit var fab: FloatingActionButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fab = findViewById(R.id.fab)
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)

        val navController: NavController =
            Navigation.findNavController(this, R.id.fragment_container)
        bottomNavigationView.background = null
        fab.setOnClickListener {
            navController.navigate(R.id.mapsFragment)
        }
        NavigationUI.setupWithNavController(bottomNavigationView, navController)
        findNavController(R.id.fragment_container)
    }

}