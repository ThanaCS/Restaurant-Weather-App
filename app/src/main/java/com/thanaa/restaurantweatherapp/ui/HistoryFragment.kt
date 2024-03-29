package com.thanaa.restaurantweatherapp.ui

import android.app.AlertDialog
import android.graphics.Color
import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.androidadvance.topsnackbar.TSnackbar
import com.thanaa.restaurantweatherapp.R
import com.thanaa.restaurantweatherapp.adapter.RestaurantAdapter
import com.thanaa.restaurantweatherapp.database.AppDatabase
import com.thanaa.restaurantweatherapp.databinding.FragmentHistoryBinding
import com.thanaa.restaurantweatherapp.repository.HistoryRepository
import com.thanaa.restaurantweatherapp.viewmodel.HistoryViewModel
import com.thanaa.restaurantweatherapp.viewmodel.providerfactory.HistoryProviderFactory

class HistoryFragment : Fragment() {
    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!
    private lateinit var historyViewModel: HistoryViewModel
    lateinit var adapter: RestaurantAdapter
    private val args by navArgs<HistoryFragmentArgs>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (activity as MainActivity).supportActionBar?.title = getString(R.string.history)
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)

        setData()

        //Menu of Deletion and Sorting
        setHasOptionsMenu(true)
        return binding.root
    }

    private fun setData() {
        val repository = HistoryRepository(AppDatabase.getDatabase(requireContext()))
        val factory = HistoryProviderFactory(repository)
        historyViewModel = ViewModelProvider(this, factory).get(HistoryViewModel::class.java)
        historyViewModel.getAllData.observe(viewLifecycleOwner, {
            binding.recyclerview.layoutManager = LinearLayoutManager(requireActivity())
            adapter = RestaurantAdapter(it, 2)
            binding.recyclerview.adapter = adapter
            historyViewModel.checkIfDatabaseEmpty(it)

        })
        //check if the database is empty to show empty view
        historyViewModel.emptyDatabase.observe(viewLifecycleOwner, {
            showEmptyView(it)
        })
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

    private fun confirmRemoval() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton(getString(R.string.yes)) { _, _ ->
            historyViewModel.deleteAll()
            val snackbar = TSnackbar.make(
                requireView(),
                getString(R.string.history_successfully_deleted),
                TSnackbar.LENGTH_LONG
            )
            snackbar.setActionTextColor(Color.WHITE)
            val snackbarView = snackbar.view
            snackbarView.setBackgroundColor(Color.parseColor("#5B9787DC"))
            val textView =
                snackbarView.findViewById(com.androidadvance.topsnackbar.R.id.snackbar_text) as TextView
            textView.setTextColor(Color.WHITE)
            snackbar.show()
        }
        builder.setNegativeButton(getString(R.string.no)) { _, _ -> }
        builder.setTitle(getString(R.string.clear_history))
        builder.setMessage(getString(R.string.are_you_sure))
        builder.create().show()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_delete_all -> confirmRemoval()
            R.id.price -> historyViewModel.sortByPrice.observe(this, { adapter.setData(it) })
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.history_menu, menu)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}

