package com.thanaa.restaurantweatherapp

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.thanaa.restaurantweatherapp.databinding.FragmentMapsBinding

class MapsFragment : Fragment() {
    private var PERMISSION_ID: Int = 1
    private var latValue: Double = 0.0
    private var lonValue: Double = 0.0
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
        binding.ok.setOnClickListener {
            val action = MapsFragmentDirections.actionMapsFragmentToHomeFragment(
                binding.search.toString(),
                latValue.toString(),
                lonValue.toString()
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
            MarkerOptions().position(
                LatLng(lan, lon)
            )
                .draggable(true)
        )

        googleMap.setOnMarkerDragListener(object : GoogleMap.OnMarkerDragListener {
            override fun onMarkerDragStart(arg0: Marker) {

            }

            override fun onMarkerDragEnd(arg0: Marker) {
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(arg0.position, 1.0f))
                val message =
                    arg0.position.latitude.toString() + "" + arg0.position.longitude.toString()
                Toast.makeText(
                    context,
                    "$message   ${LatLng(arg0.position.latitude, arg0.position.longitude)}",
                    Toast.LENGTH_SHORT
                ).show()
                Log.d(TAG, "${arg0.position.latitude} ,${arg0.position.longitude} ")
                latValue = arg0.position.latitude
                lonValue = arg0.position.longitude

            }

            override fun onMarkerDrag(arg0: Marker?) {
                val message =
                    arg0!!.position.latitude.toString() + "" + arg0.position.longitude.toString()
                Toast.makeText(context, "$message", Toast.LENGTH_SHORT).show()
                Log.d(TAG, "${arg0.position.latitude} ,${arg0.position.longitude} ")
                latValue = arg0.position.latitude
                lonValue = arg0.position.longitude
            }
        })
    }

}