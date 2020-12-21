package com.thanaa.restaurantweatherapp

import android.app.AlertDialog
import android.graphics.Color
import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.androidadvance.topsnackbar.TSnackbar
import com.google.android.material.snackbar.Snackbar
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
            binding.recyclerview.adapter = RestaurantAdapter(it, 2)
        })
        //Menu of Deletion and Sorting
        setHasOptionsMenu(true)
        return binding.root
    }

    private fun confirmRemoval() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton(getString(R.string.yes)) { _, _ ->
            viewModelDB.deleteAll()
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

            Snackbar.make(
                requireView(),
                getString(R.string.history_successfully_deleted),
                Snackbar.LENGTH_LONG
            ).setBackgroundTint(ContextCompat.getColor(requireContext(), R.color.black))
                .show()
        }
        builder.setNegativeButton(getString(R.string.no)) { _, _ -> }
        builder.setTitle(getString(R.string.clear_history))
        builder.setMessage(getString(R.string.are_you_sure))
        builder.create().show()
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}

