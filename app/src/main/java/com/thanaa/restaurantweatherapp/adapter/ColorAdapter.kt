package com.thanaa.restaurantweatherapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.thanaa.restaurantweatherapp.databinding.ColorRowBinding

class ColorAdapter(context: Context, countryList: List<Int>) :
    ArrayAdapter<Int>(context, 0, countryList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return initView(position, parent)
    }

    override fun getDropDownView(
        position: Int, convertView: View?, parent: ViewGroup
    ): View? {
        return initView(position, parent)
    }

    private fun initView(position: Int, parent: ViewGroup): View {
        val binding = ColorRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        val currentItem = getItem(position)
        if (currentItem != null) {
            binding.imageViewColor.setImageResource(currentItem)
        }
        return binding.root
    }
}
