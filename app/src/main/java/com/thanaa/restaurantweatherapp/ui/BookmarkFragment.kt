package com.thanaa.restaurantweatherapp.ui

import android.app.AlertDialog
import android.graphics.Color
import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.androidadvance.topsnackbar.TSnackbar
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.auth.FirebaseAuth
import com.thanaa.restaurantweatherapp.R
import com.thanaa.restaurantweatherapp.adapter.BookmarkAdapter
import com.thanaa.restaurantweatherapp.database.AppDatabase
import com.thanaa.restaurantweatherapp.databinding.FragmentBookmarkBinding
import com.thanaa.restaurantweatherapp.repository.BookmarkRepository
import com.thanaa.restaurantweatherapp.viewmodel.BookmarkViewModel
import com.thanaa.restaurantweatherapp.viewmodel.providerfactory.BookmarkProviderFactory

class BookmarkFragment : Fragment() {
    private var _binding: FragmentBookmarkBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth
    private lateinit var bookmarkViewModel: BookmarkViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBookmarkBinding.inflate(inflater, container, false)
        (activity as MainActivity).supportActionBar?.title = getString(R.string.Bookmark)
        setData()
        getUserInfo()
        setHasOptionsMenu(true)
        return binding.root
    }

    private fun setData() {
        val repository = BookmarkRepository(AppDatabase.getDatabase(requireContext()))
        val factory = BookmarkProviderFactory(repository)
        bookmarkViewModel = ViewModelProvider(this, factory).get(BookmarkViewModel::class.java)
        binding.recyclerView.layoutManager = LinearLayoutManager(requireActivity())
        bookmarkViewModel.getAllData.observe(viewLifecycleOwner, {
            bookmarkViewModel.checkIfDatabaseEmpty(it)
            binding.recyclerView.adapter = BookmarkAdapter(it, 1)

        })
        //check if the database is empty to show empty view
        bookmarkViewModel.emptyDatabase.observe(viewLifecycleOwner, {
            showEmptyView(it)
        })
    }

    private fun getUserInfo() {
        //Firebase auth instance
        auth = FirebaseAuth.getInstance()
        //set up auth image to image view
        Glide.with(this).load(auth.currentUser?.photoUrl)
            .apply(RequestOptions.circleCropTransform())
            .into(binding.profileImage)
        //set up profile info
        binding.username.text = auth.currentUser?.displayName
        binding.email.text = auth.currentUser?.email
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_delete_all -> confirmRemoval()


        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.options_menu, menu)
    }

    private fun confirmRemoval() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton(getString(R.string.yes)) { _, _ ->
            bookmarkViewModel.deleteAll()
            val snackbar = TSnackbar.make(
                requireView(),
                getString(R.string.bookmark_successfully_deleted),
                TSnackbar.LENGTH_LONG
            )
            snackbar.setActionTextColor(Color.WHITE)
            val snackbarView = snackbar.view
            snackbarView.setBackgroundColor(Color.parseColor(getString(R.string.light_pink)))
            val textView =
                snackbarView.findViewById(com.androidadvance.topsnackbar.R.id.snackbar_text) as TextView
            textView.setTextColor(Color.WHITE)
            snackbar.show()
        }
        builder.setNegativeButton(getString(R.string.no)) { _, _ -> }
        builder.setTitle(getString(R.string.clear_bookmark))
        builder.setMessage(getString(R.string.are_you_sure_bookmark))
        builder.create().show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showEmptyView(emptyBusinesses: Boolean) {
        if (emptyBusinesses) {
            binding.sadCatEmpty.visibility = View.VISIBLE
            binding.emptyText.visibility = View.VISIBLE
        } else {
            binding.sadCatEmpty.visibility = View.INVISIBLE
            binding.emptyText.visibility = View.INVISIBLE
        }
    }

}