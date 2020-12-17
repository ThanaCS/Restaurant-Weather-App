package com.thanaa.restaurantweatherapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.thanaa.restaurantweatherapp.databinding.FragmentHomeBinding
import com.thanaa.restaurantweatherapp.viewmodel.YelpViewModel

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: YelpViewModel
    private val args by navArgs<HomeFragmentArgs>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(YelpViewModel::class.java)
        setData()

        return binding.root
    }

    private fun setData() {
        binding.progressbar.visibility = View.VISIBLE
        viewModel.getBusinessesFromLanLon(args.food, args.lon, args.lat)
        viewModel.businessesLanLonLiveData.observe(viewLifecycleOwner, {
            binding.recyclerview.layoutManager = LinearLayoutManager(requireActivity())
            binding.recyclerview.adapter = RestaurantAdapter(it)
            binding.progressbar.visibility = View.GONE
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}