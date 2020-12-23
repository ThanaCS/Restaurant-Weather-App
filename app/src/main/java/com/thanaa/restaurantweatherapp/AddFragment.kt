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
        setCountries()
        setColors()
        return binding.root
    }

    private fun setCountries() {
        binding.spinnerCountries.adapter = CountryAdapter(requireContext(), listCountry())
        binding.spinnerCountries.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                val country = parent.getItemAtPosition(position) as CountryItem

                Toast.makeText(
                    requireContext(),
                    "selected, ${country.flagImage} ${country.countryName}",
                    Toast.LENGTH_LONG
                ).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}

        }
    }

    private fun setColors() {
        binding.spinnerColors.adapter = ColorAdapter(requireContext(), listColor())
        binding.spinnerColors.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                val color = parent.getItemAtPosition(position)
                Toast.makeText(
                    requireContext(),
                    "$color selected",
                    Toast.LENGTH_SHORT
                ).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}

        }
    }


    private fun listCountry(): List<CountryItem> {
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
        return country
    }

    private fun listColor(): List<Int> {
        val colors = listOf(
            R.drawable.red, R.drawable.oragne, R.drawable.yellow,
            R.drawable.light_green,
            R.drawable.green,
            R.drawable.purple,
            R.drawable.ic_restaurant,
            R.drawable.ic_assistant,
            R.drawable.ic_shopping,
            R.drawable.ic_fastfood,
            R.drawable.ic_beverage,
            R.drawable.ic_airplan,
            R.drawable.ic_eco,
            R.drawable.ic_restaurant2,
            R.drawable.ic_store
        )
        return colors
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}