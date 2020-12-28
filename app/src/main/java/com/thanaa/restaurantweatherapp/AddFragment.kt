package com.thanaa.restaurantweatherapp

import android.os.Bundle
import android.text.TextUtils
import android.text.format.DateFormat
import android.view.*
import android.widget.AdapterView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.thanaa.restaurantweatherapp.adapter.ColorAdapter
import com.thanaa.restaurantweatherapp.adapter.CountryAdapter
import com.thanaa.restaurantweatherapp.databinding.FragmentAddBinding
import com.thanaa.restaurantweatherapp.model.CountryItem
import com.thanaa.restaurantweatherapp.model.Plan
import com.thanaa.restaurantweatherapp.ui.DatePickerFragment
import com.thanaa.restaurantweatherapp.ui.MainActivity
import com.thanaa.restaurantweatherapp.viewmodel.PlanViewModel
import java.util.*

const val REQUEST_DATE = 0
const val DIALOG_DATE = "DialogDate"

class AddFragment : Fragment(), DatePickerFragment.Callbacks {
    private var _binding: FragmentAddBinding? = null
    private val binding get() = _binding!!
    private lateinit var plan: Plan
    private var flag: Int = 0
    private var color: Int = 0
    private var location = ""
    private val planViewModel: PlanViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity as MainActivity).supportActionBar?.title = getString(R.string.add_plan)
        val initDate = Calendar.getInstance().time
        plan = Plan(0, "", "", initDate, null, "", null)


    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddBinding.inflate(inflater, container, false)


        setCountries()
        setColors()
        setHasOptionsMenu(true)
        binding.date.setOnClickListener {
            DatePickerFragment.newInstance(plan.date).apply {
                setTargetFragment(this@AddFragment, REQUEST_DATE)
                show(this@AddFragment.requireFragmentManager(), DIALOG_DATE)
            }

        }
        return binding.root
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.add_fragment_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_add) {
            insertDataToDb()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun insertDataToDb() {
        val title = binding.title.text.toString()
        val description = binding.description.text.toString()
        if (!TextUtils.isEmpty(title) && !TextUtils.isEmpty(description)) {
            val newData = Plan(0, title, description, plan.date, color, location, flag)
            planViewModel.insertData(newData)
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
        plan.date = date
        val dateFormat = DateFormat.format("EEEE, MMM, dd", plan.date).toString()
        binding.dateText.text = dateFormat
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}