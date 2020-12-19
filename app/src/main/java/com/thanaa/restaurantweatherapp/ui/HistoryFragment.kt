package com.thanaa.restaurantweatherapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.thanaa.restaurantweatherapp.HistoryFragmentArgs
import com.thanaa.restaurantweatherapp.databinding.FragmentHistoryBinding
import com.thanaa.restaurantweatherapp.viewmodel.DatabaseViewModel


class HistoryFragment : Fragment() {
    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!
    lateinit var viewModel: DatabaseViewModel
    private val args by navArgs<HistoryFragmentArgs>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        viewModel = (activity as MainActivity).viewModel

        setData()
        return binding.root
    }

    private fun setData() {

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}

