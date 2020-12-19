package com.thanaa.restaurantweatherapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import com.thanaa.restaurantweatherapp.viewmodel.WeatherViewModel
import kotlin.time.ExperimentalTime
import kotlin.time.hours

class InfoFragment : Fragment() {
    private var _binding: FragmentInfoBinding? = null
    private val binding get() = _binding!!
    private val args by navArgs<InfoFragmentArgs>()
    private lateinit var weatherViewModel: WeatherViewModel
    private var TAG = "InfoFragment"

    @ExperimentalTime
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInfoBinding.inflate(inflater, container, false)
        setData()
        return binding.root
    }

    @ExperimentalTime
    private fun setData() {
        weatherViewModel = ViewModelProvider(this).get(WeatherViewModel::class.java)
        binding.apply {
            weatherRecyclerView1.layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.HORIZONTAL, false
            )
            weatherRecyclerView2.layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.HORIZONTAL, false
            )
            weatherRecyclerView3.layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.HORIZONTAL, false
            )
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
            ratingNumber.text = "(${args.business.review_count})"
            Glide.with(binding.imageView)
                .load(args.business.image_url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .apply(RequestOptions().transform(CenterCrop(), RoundedCorners(10)))
                .into(binding.imageView)

            progressBar.visibility = View.VISIBLE
            val lat = args.business.coordinates.latitude
            val lon = args.business.coordinates.longitude
            weatherViewModel.getWeather("$lat,$lon")
            weatherViewModel.weatherLiveData.observe(viewLifecycleOwner, {
                binding.apply {
                    weatherRecyclerView1.layoutManager = LinearLayoutManager(
                        requireActivity(),
                        LinearLayoutManager.HORIZONTAL,
                        false
                    )
                    weatherRecyclerView1.adapter =
                        WeatherAdapter(it.forecast.forecastday[0].hour)

                    weatherRecyclerView2.layoutManager = LinearLayoutManager(
                        requireActivity(),
                        LinearLayoutManager.HORIZONTAL,
                        false
                    )
                    weatherRecyclerView2.adapter =
                        WeatherAdapter(it.forecast.forecastday[1].hour)

                    weatherRecyclerView3.layoutManager = LinearLayoutManager(
                        requireActivity(),
                        LinearLayoutManager.HORIZONTAL,
                        false
                    )

                    weatherRecyclerView3.adapter =
                        WeatherAdapter(it.forecast.forecastday[2].hour)
                    val date1 = it.forecast.forecastday[0].hour[0].time.substringBefore(" ")
                    val date2 = it.forecast.forecastday[1].hour[0].time.substringBefore(" ")
                    val date3 = it.forecast.forecastday[2].hour[0].time.substringBefore(" ")
                    binding.date1.text = date1
                    binding.date2.text = date2
                    binding.date3.text = date3

                }

            })

            progressBar.visibility = View.GONE

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}
