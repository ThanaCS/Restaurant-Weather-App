package com.thanaa.restaurantweatherapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.thanaa.restaurantweatherapp.databinding.FragmentHistoryBinding
import com.thanaa.restaurantweatherapp.ui.homeFragment.RestaurantAdapter
import com.thanaa.restaurantweatherapp.viewmodel.DatabaseViewModel

class HistoryFragment : Fragment() {
    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!
    private val viewModelDB: DatabaseViewModel by viewModels()
    private val args by navArgs<HistoryFragmentArgs>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        viewModelDB.getAllData.observe(viewLifecycleOwner, {
            binding.recyclerview.layoutManager = LinearLayoutManager(requireActivity())
            binding.recyclerview.adapter = RestaurantAdapter(it)
        })
        return binding.root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}

