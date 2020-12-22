package com.thanaa.restaurantweatherapp.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.thanaa.restaurantweatherapp.R


class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"
    lateinit var fab: FloatingActionButton
    lateinit var bottomNavigationView: BottomNavigationView
    lateinit var bottomAppBar: BottomAppBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        fab = findViewById(R.id.fab)
        bottomNavigationView = findViewById(R.id.bottomNavigationView)
        bottomAppBar = findViewById(R.id.bottomAppBar)
        showNavigation()
        val navController: NavController =
            Navigation.findNavController(this, R.id.fragment_container)
        bottomNavigationView.background = null
        fab.setOnClickListener {
            navController.navigate(R.id.mapsFragment)
        }
        NavigationUI.setupWithNavController(bottomNavigationView, navController)
        findNavController(R.id.fragment_container)
    }

    private fun showNavigation() {
        bottomNavigationView.visibility = View.VISIBLE
        bottomAppBar.visibility = View.VISIBLE
        fab.visibility = View.VISIBLE
    }

}