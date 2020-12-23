package com.thanaa.restaurantweatherapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.thanaa.restaurantweatherapp.databinding.FragmentAddBinding
import com.thanaa.restaurantweatherapp.model.CountryItem

class AddFragment : Fragment() {
    private var _binding: FragmentAddBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddBinding.inflate(inflater, container, false)
        val country = listOf(
            CountryItem("Germany", R.drawable.germany),
            CountryItem("France", R.drawable.france),
            CountryItem("United State", R.drawable.united_state),
            CountryItem("China", R.drawable.china),
            CountryItem("Italy", R.drawable.italy),
            CountryItem("Japan", R.drawable.japan),
            CountryItem("Spain", R.drawable.spain),
            CountryItem("Russia", R.drawable.russia),
            CountryItem("United Kingdom", R.drawable.united_kingdom),
            CountryItem("South Korea", R.drawable.south_korea)
        )

        binding.spinnerCountries.adapter = CountryAdapter(requireContext(), country)
        binding.spinnerCountries.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                val (clickedCountryName) = parent.getItemAtPosition(position) as CountryItem
                Toast.makeText(
                    requireContext(),
                    "$clickedCountryName selected",
                    Toast.LENGTH_SHORT
                ).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }
        return binding.root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}