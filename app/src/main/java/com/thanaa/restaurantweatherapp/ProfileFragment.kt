package com.thanaa.restaurantweatherapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.auth.FirebaseAuth
import com.thanaa.restaurantweatherapp.databinding.FragmentProfileBinding


class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        //Firebase auth instance
        auth = FirebaseAuth.getInstance()

        //set up auth image to image view
        Glide.with(this).load(auth.currentUser?.photoUrl)
            .apply(RequestOptions.circleCropTransform())
            .into(binding.imageView)
        //set up username
        binding.username.text = auth.currentUser?.displayName
        binding.email.text = auth.currentUser?.email

        binding.logoutButton.setOnClickListener {
            auth.signOut()
            findNavController().navigate(R.id.loginFragment)
        }
        if (auth.currentUser == null) {
            findNavController().navigate(R.id.loginFragment)
        }


        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}