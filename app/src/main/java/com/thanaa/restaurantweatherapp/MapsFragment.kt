package com.thanaa.restaurantweatherapp

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
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
import com.google.android.material.snackbar.Snackbar
import com.thanaa.restaurantweatherapp.databinding.FragmentMapsBinding
import com.thanaa.restaurantweatherapp.viewmodel.WeatherViewModel

class MapsFragment : Fragment(), SearchView.OnQueryTextListener {
    private var PERMISSION_ID: Int = 1
    private var latValue: Double = 0.0
    private var lonValue: Double = 0.0
    private var food: String? = ""
    private var weather = ""
    private var country: String = ""
    private lateinit var weatherViewModel: WeatherViewModel
    lateinit var fusedLocationClient: FusedLocationProviderClient
    private val TAG = "MapsFragment"
    private var _binding: FragmentMapsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMapsBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        weatherViewModel = ViewModelProvider(this).get(WeatherViewModel::class.java)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getUserLocation()

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
            } != PackageManager.PERMISSION_GRANTED
        ) {


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
        //Pass data to home fragment
        binding.submitButton.setOnClickListener {
            val action = MapsFragmentDirections.actionMapsFragmentToHomeFragment(
                food!!,
                latValue.toString(),
                lonValue.toString(), country, weather
            )
            findNavController().navigate(action)
        }
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
    }

    @SuppressLint("PotentialBehaviorOverride", "MissingPermission")
    private fun onMapReady(googleMap: GoogleMap, lan: Double, lon: Double) {
        googleMap.addMarker(
            MarkerOptions().position(LatLng(lan, lon))
                .draggable(true)
                .icon(
                    BitmapDescriptorFactory
                        .defaultMarker(BitmapDescriptorFactory.HUE_VIOLET)
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
                    Toast.makeText(context, "~${it.current.condition.text}~", Toast.LENGTH_SHORT)
                        .show()
                    Snackbar.make(
                        requireView(),
                        "${it.location.country},${it.location.region}",
                        Snackbar.LENGTH_LONG
                    ).setBackgroundTint(ContextCompat.getColor(requireContext(), R.color.black))
                        .show()
                    country = it.location.country
                    weather = it.current.condition.text
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


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu, menu)
        val search = menu.findItem(R.id.menu_search)
        val searchView = search.actionView as? SearchView
        searchView?.isSubmitButtonEnabled = true
        searchView?.setOnQueryTextListener(this)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query != null) {
            food = query
            //Hide soft keys
            hideKeyBoard()
        }
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        if (newText != null) {
            food = newText
        }
        return true
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
}