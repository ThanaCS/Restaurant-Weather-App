package com.thanaa.restaurantweatherapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.thanaa.restaurantweatherapp.databinding.FragmentPlanBinding
import com.thanaa.restaurantweatherapp.viewmodel.PlanViewModel
import com.thanaa.restaurantweatherapp.viewmodel.SharedViewModel


class PlanFragment : Fragment() {
    private var _binding: FragmentPlanBinding? = null
    private val binding get() = _binding!!
    private val planViewModel: PlanViewModel by viewModels()
    private val sharedViewModel: SharedViewModel by viewModels()
    lateinit var recyclerView: RecyclerView
    private val adapter: PlanAdapter by lazy { PlanAdapter() }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPlanBinding.inflate(inflater, container, false)

        recyclerView = binding.recyclerView
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireActivity())
        planViewModel.getAllData.observe(viewLifecycleOwner, {
            adapter.setData(it)

        })

        binding.addButton.setOnClickListener {
            findNavController().navigate((R.id.addFragment))
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
