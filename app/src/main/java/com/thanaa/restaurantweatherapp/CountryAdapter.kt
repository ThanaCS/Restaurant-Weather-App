package com.thanaa.restaurantweatherapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.thanaa.restaurantweatherapp.databinding.CountyRowBinding
import com.thanaa.restaurantweatherapp.model.CountryItem

class CountryAdapter(context: Context, countryList: List<CountryItem>) :
    ArrayAdapter<CountryItem>(context, 0, countryList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return initView(position, parent)
    }

    override fun getDropDownView(
        position: Int, convertView: View?, parent: ViewGroup
    ): View? {
        return initView(position, parent)
    }

    private fun initView(position: Int, parent: ViewGroup): View {
        val binding = CountyRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        val currentItem = getItem(position)
        if (currentItem != null) {
            binding.imageViewFlag.setImageResource(currentItem.flagImage)
            binding.viewNameCountry.text = currentItem.countryName
        }
        return binding.root
    }
}
