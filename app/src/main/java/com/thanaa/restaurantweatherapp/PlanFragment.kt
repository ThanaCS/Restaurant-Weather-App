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
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.auth.FirebaseAuth
import com.thanaa.restaurantweatherapp.databinding.FragmentPlanBinding
import com.thanaa.restaurantweatherapp.ui.MainActivity
import com.thanaa.restaurantweatherapp.viewmodel.PlanViewModel
import com.thanaa.restaurantweatherapp.viewmodel.SharedViewModel


class PlanFragment : Fragment() {
    private var _binding: FragmentPlanBinding? = null
    private val binding get() = _binding!!
    private val planViewModel: PlanViewModel by viewModels()
    private val sharedViewModel: SharedViewModel by viewModels()
    lateinit var recyclerView: RecyclerView
    private lateinit var auth: FirebaseAuth
    private val adapter: PlanAdapter by lazy { PlanAdapter() }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPlanBinding.inflate(inflater, container, false)
        firebaseProfile()
        setData()
        (activity as MainActivity).supportActionBar?.title = getString(R.string.plans)
        binding.addButton.setOnClickListener {
            findNavController().navigate((R.id.addFragment))
        }
        return binding.root
    }

    private fun firebaseProfile() {
        //Firebase auth instance
        auth = FirebaseAuth.getInstance()

        //set up auth image to image view
        Glide.with(this).load(auth.currentUser?.photoUrl)
            .apply(RequestOptions.circleCropTransform())
            .into(binding.profileImage)
        //set up username
        binding.username.text = auth.currentUser?.displayName
        binding.email.text = auth.currentUser?.email
    }

    private fun setData() {
        recyclerView = binding.recyclerView
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireActivity())
        planViewModel.getAllData.observe(viewLifecycleOwner, {
            adapter.setData(it)

        })

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
