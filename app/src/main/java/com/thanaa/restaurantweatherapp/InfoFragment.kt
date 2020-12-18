package com.thanaa.restaurantweatherapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.thanaa.restaurantweatherapp.databinding.FragmentInfoBinding
import com.thanaa.restaurantweatherapp.ui.WeatherAdapter
import com.thanaa.restaurantweatherapp.viewmodel.WeatherViewModel
import kotlin.time.ExperimentalTime
import kotlin.time.hours

class InfoFragment : Fragment() {
    private var _binding: FragmentInfoBinding? = null
    private val binding get() = _binding!!
    private val args by navArgs<InfoFragmentArgs>()
    private lateinit var weatherViewModel: WeatherViewModel
    @ExperimentalTime
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInfoBinding.inflate(inflater, container, false)
        binding.weatherRecyclerView.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.HORIZONTAL, false
        )
        weatherViewModel = ViewModelProvider(this).get(WeatherViewModel::class.java)
        setData()
        return binding.root
    }

    @ExperimentalTime
    private fun setData() {
        binding.apply {
            progressBar.visibility = View.VISIBLE
            isOpen.text = if (args.business.is_closed) "Closed" else "Open"
            name.text = args.business.name
            distance.text = String.format("%.0f", args.business.distance.hours.inDays) + " hours"
            category.text = args.business.categories[0].title
            //TODO:user can call the number
            phoneNumber.text = args.business.phone
            region.text = "${args.business.location.city}, ${args.business.location.country}"
            price.text = args.business.price
            ratingBar.rating = args.business.rating.toFloat()
            binding.ratingNumber.text = "(${args.business.review_count})"
            Glide.with(binding.imageView)
                .load(args.business.image_url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .apply(RequestOptions().transform(CenterCrop(), RoundedCorners(10)))
                .into(binding.imageView)
        }
        val lat = args.business.coordinates.latitude
        val lon = args.business.coordinates.longitude
        weatherViewModel.getWeather("$lat,$lon")
        weatherViewModel.weatherLiveData.observe(viewLifecycleOwner, {
            Toast.makeText(context, "${it.forecast.forecastday[0]}", Toast.LENGTH_SHORT).show()
            binding.weatherRecyclerView.layoutManager =
                LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
            binding.weatherRecyclerView.adapter = WeatherAdapter(it.forecast.forecastday[0].hour)
            binding.weatherRecyclerView.visibility = View.GONE
        })


        binding.progressBar.visibility = View.GONE


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}
