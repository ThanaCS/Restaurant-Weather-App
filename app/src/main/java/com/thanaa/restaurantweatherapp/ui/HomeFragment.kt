package com.thanaa.restaurantweatherapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.thanaa.restaurantweatherapp.databinding.FragmentHomeBinding
import com.thanaa.restaurantweatherapp.viewmodel.WeatherViewModel
import com.thanaa.restaurantweatherapp.viewmodel.YelpViewModel

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var yelpViewModel: YelpViewModel
    private lateinit var weatherViewModel: WeatherViewModel

    private val args by navArgs<HomeFragmentArgs>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        yelpViewModel = ViewModelProvider(this).get(YelpViewModel::class.java)
        weatherViewModel = ViewModelProvider(this).get(WeatherViewModel::class.java)
        setData()
        return binding.root
    }

    private fun setData() {
        binding.progressbar.visibility = View.VISIBLE
        Toast.makeText(context, "${args.country}", Toast.LENGTH_SHORT).show()
        yelpViewModel.getBusinesses(term = args.food, location = args.country)
        yelpViewModel.businessesLiveData.observe(viewLifecycleOwner, {
            binding.recyclerview.layoutManager = LinearLayoutManager(requireActivity())
            binding.recyclerview.adapter = RestaurantAdapter(it, args.weather)
            binding.progressbar.visibility = View.GONE
        })

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}