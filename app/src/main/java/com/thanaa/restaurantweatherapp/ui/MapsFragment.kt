package com.thanaa.restaurantweatherapp.ui

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.androidadvance.topsnackbar.TSnackbar
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.thanaa.restaurantweatherapp.R
import com.thanaa.restaurantweatherapp.databinding.FragmentMapsBinding
import com.thanaa.restaurantweatherapp.viewmodel.WeatherViewModel
import com.thanaa.restaurantweatherapp.weatherModel.WeatherResponse


class MapsFragment : Fragment(), View.OnClickListener {
    private var PERMISSION_ID: Int = 1
    private var latValue: Double = 0.0
    private var lonValue: Double = 0.0
    private var food: String? = ""
    private var weather: WeatherResponse? = null
    private lateinit var weatherViewModel: WeatherViewModel
    lateinit var fusedLocationClient: FusedLocationProviderClient
    private val TAG = "MapsFragment"
    private var _binding: FragmentMapsBinding? = null
    lateinit var fab: FloatingActionButton
    lateinit var bottomNavigationView: BottomNavigationView
    lateinit var bottomAppBar: BottomAppBar
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMapsBinding.inflate(inflater, container, false)
        getUserLocation()
        showNavigation()
        autoCompleteSearch()
        setData()

        return binding.root
    }

    private fun setData() {
        weatherViewModel = ViewModelProvider(this).get(WeatherViewModel::class.java)
        weatherViewModel.getWeather("${latValue},${lonValue}")
        weatherViewModel.weatherLiveData.observe(viewLifecycleOwner, {
            weather = it
        })
    }


    private fun getUserLocation() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),
                PERMISSION_ID
            )
        }
        fusedLocationClient.lastLocation.addOnSuccessListener {
            if (it != null) {
                weatherViewModel.getWeather("${it.longitude},${it.latitude}")
                latValue = it.latitude
                lonValue = it.longitude
            }
        }

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        if (context?.let {
                ActivityCompat.checkSelfPermission(
                    it,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            } != PackageManager.PERMISSION_GRANTED && context?.let {
                ActivityCompat.checkSelfPermission(
                    it,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            } != PackageManager.PERMISSION_GRANTED) {
            return
        }
        fusedLocationClient.lastLocation.addOnSuccessListener {
            val callback = OnMapReadyCallback { googleMap ->
                latValue = it.latitude
                lonValue = it.longitude
                onMapReady(googleMap, it.latitude, it.longitude)
            }
            mapFragment?.getMapAsync(callback)
        }
    }

    @SuppressLint("PotentialBehaviorOverride", "MissingPermission")
    private fun onMapReady(googleMap: GoogleMap, lan: Double, lon: Double) {

        googleMap.addMarker(
            MarkerOptions().position(LatLng(lan, lon))
                .draggable(true)
                .icon(
                    BitmapDescriptorFactory
                        .defaultMarker(BitmapDescriptorFactory.HUE_AZURE)
                )
        )

        googleMap.setOnMarkerDragListener(object : GoogleMap.OnMarkerDragListener {
            override fun onMarkerDragStart(arg0: Marker) {
            }

            override fun onMarkerDragEnd(arg0: Marker) {
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(arg0.position, 1.0f))
                latValue = arg0.position.latitude
                lonValue = arg0.position.longitude

                weatherViewModel.getWeather("${latValue},${lonValue}")
                weatherViewModel.weatherLiveData.observe(viewLifecycleOwner, {
                    //snackbar to show the country, region, and forecast
                    val snackbar = TSnackbar.make(
                        requireView(),
                        " ${it.location.country}, ${it.location.region}\n ~${it.current.condition.text}~",
                        TSnackbar.LENGTH_LONG
                    )
                    snackbar.setActionTextColor(Color.WHITE)
                    val snackbarView = snackbar.view
                    snackbarView.setBackgroundColor(Color.parseColor("#5B9787DC"))
                    snackbar.setIconLeft(R.drawable.weathericon, 23F)
                    val textView =
                        snackbarView.findViewById(com.androidadvance.topsnackbar.R.id.snackbar_text) as TextView
                    textView.setTextColor(Color.WHITE)
                    snackbar.show()
                    //zoom in animation
                    googleMap.animateCamera(
                        CameraUpdateFactory.newLatLngZoom(
                            LatLng(
                                it.location.lat,
                                it.location.lon
                            ), 7.0f
                        )
                    )
                    weather = it
                })

            }

            override fun onMarkerDrag(arg0: Marker?) {
                if (arg0 != null) {
                    latValue = arg0.position.latitude
                }
                if (arg0 != null) {
                    lonValue = arg0.position.longitude
                }

            }
        })
    }


    override fun onClick(v: View?) {
        val query = binding.autoCompleteTextView.text.toString()

        if (query != "") {
            food = query
            //Pass data to home fragment
            val action = MapsFragmentDirections.actionMapsFragmentToHomeFragment(
                food!!,
                latValue.toString(),
                lonValue.toString(),
                weather!!
            )
            findNavController().navigate(action)
            //Hide soft keys
            hideKeyBoard()
        }
    }

    private fun autoCompleteSearch() {
        val foodAutoComplete = resources.getStringArray(R.array.food)
        val arrayAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_dropdown_item_1line,
            foodAutoComplete
        )
        binding.autoCompleteTextView.setAdapter(arrayAdapter)
        binding.autoCompleteTextView.setOnClickListener(this)
    }

    private fun hideKeyBoard() {
        activity?.let {
            val inputManager =
                it.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            val view = it.currentFocus
            if (view != null) {
                inputManager.hideSoftInputFromWindow(
                    view.windowToken,
                    InputMethodManager.HIDE_NOT_ALWAYS
                )
            }
        }
    }

    private fun showNavigation() {
        bottomNavigationView = (activity as MainActivity).bottomNavigationView
        fab = (activity as MainActivity).fab
        bottomAppBar = (activity as MainActivity).bottomAppBar
        bottomNavigationView.visibility = View.VISIBLE
        bottomAppBar.visibility = View.VISIBLE
        fab.visibility = View.VISIBLE
    }
}