package com.thanaa.restaurantweatherapp.ui

import android.os.Bundle
import android.text.TextUtils
import android.text.format.DateFormat
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.thanaa.restaurantweatherapp.*
import com.thanaa.restaurantweatherapp.adapter.ColorAdapter
import com.thanaa.restaurantweatherapp.adapter.CountryAdapter
import com.thanaa.restaurantweatherapp.databinding.FragmentUpdateBinding
import com.thanaa.restaurantweatherapp.model.CountryItem
import com.thanaa.restaurantweatherapp.model.Plan
import com.thanaa.restaurantweatherapp.viewmodel.PlanViewModel
import java.util.*


class UpdateFragment : Fragment(), DatePickerFragment.Callbacks {
    private val args by navArgs<UpdateFragmentArgs>()
    private val planViewModel: PlanViewModel by viewModels()
    private var _binding: FragmentUpdateBinding? = null
    private val binding get() = _binding!!
    private var flag: Int = 0
    private var color: Int = 0
    private var location = ""
    var date = Date()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUpdateBinding.inflate(inflater, container, false)
        (activity as MainActivity).supportActionBar?.title = getString(R.string.update_plan)
        setCountries()
        setColors()
        binding.date.setOnClickListener {
            DatePickerFragment.newInstance(args.plan.date).apply {
                setTargetFragment(this@UpdateFragment, REQUEST_DATE)
                show(this@UpdateFragment.requireFragmentManager(), DIALOG_DATE)
            }
        }

        flag = args.plan.icon!!
        color = args.plan.color!!
        location = args.plan.location
        //Set menu
        setHasOptionsMenu(true)
        binding.title.setText(args.plan.title)
        binding.description.setText(args.plan.description)
        val dateForamtted = DateFormat.format("EEE, MMM, dd", args.plan.date).toString()
        binding.dateText.text = dateForamtted

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.update_fragment_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.menu_save -> updateItem()
        }
        return super.onOptionsItemSelected(item)
    }


    private fun updateItem() {

        val title = binding.title.text.toString()
        val description = binding.description.text.toString()
        if (!TextUtils.isEmpty(title) && !TextUtils.isEmpty(description)) {
            val updatedItem = Plan(
                args.plan.id,
                title,
                description,
                args.plan.date,
                color,
                location,
                flag
            )
            planViewModel.updateData(updatedItem)
            Toast.makeText(requireContext(), "Successfully Added", Toast.LENGTH_SHORT).show()
            findNavController().navigate((R.id.planFragment))
        } else {
            Toast.makeText(requireContext(), "Empty Fields", Toast.LENGTH_SHORT).show()
        }


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
                flag = country.flagImage
                location = country.countryName
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

                color = parent.getItemAtPosition(position) as Int

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

    override fun onDateSelected(date: Date) {
        args.plan.date = date
        val mDate = DateFormat.format("EEE, MMM, dd", args.plan.date).toString()
        binding.dateText.text = mDate
    }


}