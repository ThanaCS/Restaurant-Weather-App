package com.thanaa.restaurantweatherapp.ui.homeFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.thanaa.restaurantweatherapp.databinding.FragmentHomeBinding
import com.thanaa.restaurantweatherapp.viewmodel.DatabaseViewModel
import com.thanaa.restaurantweatherapp.viewmodel.WeatherViewModel
import com.thanaa.restaurantweatherapp.viewmodel.YelpViewModel

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var yelpViewModel: YelpViewModel
    private lateinit var weatherViewModel: WeatherViewModel
    private val viewModelDB: DatabaseViewModel by viewModels()
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
        yelpViewModel.getBusinesses(term = args.food, location = args.weather.location.country)
        yelpViewModel.businessesLiveData.observe(viewLifecycleOwner, {
            binding.recyclerview.layoutManager = LinearLayoutManager(requireActivity())
            binding.recyclerview.adapter = RestaurantAdapter(it, 1)
            it.forEach { businesses ->
                viewModelDB.insertBusiness(businesses)
            }
            binding.progressbar.visibility = View.GONE
        })


    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}