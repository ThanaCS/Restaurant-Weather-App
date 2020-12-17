package com.thanaa.restaurantweatherapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.thanaa.restaurantweatherapp.databinding.FragmentHomeBinding
import com.thanaa.restaurantweatherapp.viewmodel.YelpViewModel

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: YelpViewModel
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(YelpViewModel::class.java)
        getData()
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getData() {
        binding.progressbar.visibility = View.VISIBLE
        viewModel.getBusinesses("pizza", "london")
        viewModel.businessesLiveData.observe(viewLifecycleOwner, {
            binding.recyclerview.layoutManager = GridLayoutManager(requireActivity(), 1)
            binding.recyclerview.adapter = RestaurantAdapter(it)
            binding.progressbar.visibility = View.GONE

        })

    }
}