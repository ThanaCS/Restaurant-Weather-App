package com.thanaa.restaurantweatherapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.thanaa.restaurantweatherapp.R
import com.thanaa.restaurantweatherapp.databinding.FragmentProfileBinding


class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth
    private val fireStoreDB = FirebaseFirestore.getInstance()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        (activity as MainActivity).supportActionBar?.title = getString(R.string.profile)

        //Firebase auth instance
        auth = FirebaseAuth.getInstance()
        getUserDetails()
        //set up auth image to image view
        Glide.with(this).load(auth.currentUser?.photoUrl)
            .apply(RequestOptions.circleCropTransform())
            .into(binding.imageView)
        //set up profile info
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

    private fun getUserDetails() {
        levelStatus()
        val docRef = fireStoreDB.collection("users").document(auth.currentUser?.uid!!)
        docRef.addSnapshotListener { snapshot, e ->
            if (snapshot != null) {
                binding.score.text = snapshot.get("score").toString()
                binding.level.text = "${snapshot.get("level")} FOODIE"
            }

        }

    }

    private fun levelStatus() {

        val docRef = fireStoreDB.collection("users").document(auth.currentUser?.uid!!)
        docRef.addSnapshotListener { snapshot, e ->
            if (snapshot != null) {

                if (snapshot.get("score") != null) {
                    val score = snapshot.get("score").toString().toInt()
                    if (score < 1000) {
                        val updates = hashMapOf<String, Any>(
                            "level" to "ROOKIE"
                        )
                        docRef.update(updates).addOnCompleteListener { }
                    }

                    if (score >= 1000) {
                        val updates = hashMapOf<String, Any>(
                            "level" to "INTERMEDIATE"
                        )
                        docRef.update(updates).addOnCompleteListener { }
                    }
                    if (score >= 50000) {
                        val updates = hashMapOf<String, Any>(
                            "level" to "EXPERT"
                        )
                        docRef.update(updates).addOnCompleteListener { }
                    }
                }
            }


        }


    }


}