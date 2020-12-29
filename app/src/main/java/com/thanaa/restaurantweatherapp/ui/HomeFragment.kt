package com.thanaa.restaurantweatherapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.thanaa.restaurantweatherapp.R
import com.thanaa.restaurantweatherapp.adapter.RestaurantAdapter
import com.thanaa.restaurantweatherapp.database.AppDatabase
import com.thanaa.restaurantweatherapp.databinding.FragmentHomeBinding
import com.thanaa.restaurantweatherapp.repository.HistoryRepository
import com.thanaa.restaurantweatherapp.viewmodel.HistoryViewModel
import com.thanaa.restaurantweatherapp.viewmodel.WeatherViewModel
import com.thanaa.restaurantweatherapp.viewmodel.YelpViewModel
import com.thanaa.restaurantweatherapp.viewmodel.providerfactory.HistoryProviderFactory

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var yelpViewModel: YelpViewModel
    private lateinit var weatherViewModel: WeatherViewModel
    private lateinit var historyViewModel: HistoryViewModel
    private val args by navArgs<HomeFragmentArgs>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (activity as MainActivity).supportActionBar?.title = getString(R.string.restaurants)
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        yelpViewModel = ViewModelProvider(this).get(YelpViewModel::class.java)
        weatherViewModel = ViewModelProvider(this).get(WeatherViewModel::class.java)
        setData()
        //check if the Businesses is empty to show empty view
        yelpViewModel.emptyBusinesses.observe(viewLifecycleOwner, {
            showEmptyView(it)
        })
        return binding.root
    }


    private fun setData() {
        val repository = HistoryRepository(AppDatabase.getDatabase(requireContext()))
        val factory = HistoryProviderFactory(repository)
        historyViewModel = ViewModelProvider(this, factory).get(HistoryViewModel::class.java)
        binding.progressbar.visibility = View.VISIBLE
        yelpViewModel.getBusinesses(term = args.food, location = args.weather.location.country)

        yelpViewModel.businessesLiveData.observe(viewLifecycleOwner, {
            binding.recyclerview.layoutManager = LinearLayoutManager(requireActivity())
            binding.recyclerview.adapter = RestaurantAdapter(it, 1)
            it.forEach { businesses ->
                historyViewModel.insertBusiness(businesses)
            }
            binding.progressbar.visibility = View.GONE


        })


    }

    private fun showEmptyView(emptyBusinesses: Boolean) {
        if (emptyBusinesses) {
            binding.emptyFood.visibility = View.VISIBLE
            binding.emptyText.visibility = View.VISIBLE
        } else {
            binding.emptyFood.visibility = View.INVISIBLE
            binding.emptyText.visibility = View.INVISIBLE

        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}