package com.thanaa.restaurantweatherapp.ui

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.thanaa.restaurantweatherapp.R
import com.thanaa.restaurantweatherapp.adapter.BookmarkAdapter
import com.thanaa.restaurantweatherapp.database.AppDatabase
import com.thanaa.restaurantweatherapp.databinding.FragmentProfileBinding
import com.thanaa.restaurantweatherapp.repository.BookmarkRepository
import com.thanaa.restaurantweatherapp.utils.Levels
import com.thanaa.restaurantweatherapp.viewmodel.BookmarkViewModel
import com.thanaa.restaurantweatherapp.viewmodel.providerfactory.BookmarkProviderFactory


class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth
    private val fireStoreDB = FirebaseFirestore.getInstance()
    private lateinit var bookmarkViewModel: BookmarkViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        (activity as MainActivity).supportActionBar?.title = getString(R.string.profile)
        setHasOptionsMenu(true)
        setData()
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


        if (auth.currentUser == null) {
            findNavController().navigate(R.id.loginFragment)
        }


        return binding.root
    }

    private fun setData() {
        val repository = BookmarkRepository(AppDatabase.getDatabase(requireContext()))
        val factory = BookmarkProviderFactory(repository)
        bookmarkViewModel = ViewModelProvider(this, factory).get(BookmarkViewModel::class.java)
        binding.recyclerView.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.HORIZONTAL, false
        )
        bookmarkViewModel.getAllData.observe(viewLifecycleOwner, {
            bookmarkViewModel.checkIfDatabaseEmpty(it)
            binding.recyclerView.adapter = BookmarkAdapter(it, 0)

        })

    }


    private fun logout() {
        auth.signOut()
        findNavController().navigate(R.id.loginFragment)
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
                            "level" to "${Levels.level1}"
                        )
                        docRef.update(updates).addOnCompleteListener { }
                    }

                    if (score >= 1000) {
                        val updates = hashMapOf<String, Any>(
                            "level" to "${Levels.level2}"
                        )
                        docRef.update(updates).addOnCompleteListener { }
                    }
                    if (score >= 50000) {
                        val updates = hashMapOf<String, Any>(
                            "level" to "${Levels.level3}"
                        )
                        docRef.update(updates).addOnCompleteListener { }
                    }
                }
            }


        }


    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.profile_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.logout -> logout()

        }
        return super.onOptionsItemSelected(item)
    }


}