package com.thanaa.restaurantweatherapp.ui

import android.animation.Animator
import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.StrictMode
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.androidadvance.topsnackbar.TSnackbar
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.firebase.FirebaseApp
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.label.ImageLabeling
import com.google.mlkit.vision.label.defaults.ImageLabelerOptions
import com.thanaa.restaurantweatherapp.R
import com.thanaa.restaurantweatherapp.adapter.WeatherAdapter
import com.thanaa.restaurantweatherapp.databinding.FragmentInfoBinding
import com.thanaa.restaurantweatherapp.model.Bookmark
import com.thanaa.restaurantweatherapp.utils.DoubleClickListener
import com.thanaa.restaurantweatherapp.viewmodel.BookmarkViewModel
import com.thanaa.restaurantweatherapp.viewmodel.WeatherViewModel
import java.io.BufferedInputStream
import java.io.IOException
import java.io.InputStream
import java.net.URL
import java.net.URLConnection
import kotlin.time.ExperimentalTime
import kotlin.time.hours


class InfoFragment : Fragment() {
    private var _binding: FragmentInfoBinding? = null
    private val binding get() = _binding!!
    private val args by navArgs<InfoFragmentArgs>()
    private lateinit var weatherViewModel: WeatherViewModel
    private val bookmarkViewModel: BookmarkViewModel by viewModels()
    private var TAG = "InfoFragment"
    override fun onCreate(savedInstanceState: Bundle?) {
        FirebaseApp.initializeApp(requireContext())
        super.onCreate(savedInstanceState)
    }

    @SuppressLint("ClickableViewAccessibility")
    @ExperimentalTime
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity as MainActivity).supportActionBar?.title = getString(R.string.details)
        _binding = FragmentInfoBinding.inflate(inflater, container, false)
        setData()
        doubleClickToBookmark()
        binding.heart.visibility = View.INVISIBLE
        return binding.root
    }


    @ExperimentalTime
    private fun setData() {
        weatherViewModel = ViewModelProvider(this).get(WeatherViewModel::class.java)
        binding.apply {
            progressBar.visibility = View.VISIBLE
            infoGroup.visibility = View.GONE
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
            phoneNumber.text = args.business.phone
            region.text = "${args.business.location.city}, ${args.business.location.country}"
            price.text = args.business.price
            ratingBar.rating = args.business.rating.toFloat()
            ratingNumber.text = "(${args.business.review_count})"
            Glide.with(binding.imageView)
                .load(args.business.image_url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .into(binding.imageView)


            val lat = args.business.coordinates.latitude
            val lon = args.business.coordinates.longitude
            weatherViewModel.getWeather("$lat,$lon")
            weatherViewModel.weatherLiveData.observe(viewLifecycleOwner, {
                binding.apply {

                    weatherRecyclerView1.adapter =
                        WeatherAdapter(it.forecast.forecastday[0].hour)

                    weatherRecyclerView2.adapter =
                        WeatherAdapter(it.forecast.forecastday[1].hour)

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
            infoGroup.visibility = View.VISIBLE


            binding.imageView.setOnClickListener {
                firebaseLabelPhotos()
            }
            //calling
            callPhone.setOnClickListener {
                callNumber(args.business.phone)
            }

        }


    }

    private fun doubleClickToBookmark() {
        binding.imageView.setOnClickListener(object : DoubleClickListener() {
            override fun onDoubleClick(v: View?) {
                binding.heart.visibility = View.VISIBLE
                binding.heart.playAnimation()
                binding.heart.addAnimatorListener(object : Animator.AnimatorListener {
                    override fun onAnimationRepeat(animation: Animator?) {
                    }

                    override fun onAnimationEnd(animation: Animator?) {
                        binding.heart.visibility = View.GONE
                    }

                    override fun onAnimationCancel(animation: Animator?) {
                    }

                    override fun onAnimationStart(animation: Animator?) {

                    }

                })
                val bookmark = Bookmark(
                    args.business.id,
                    args.business.name,
                    args.business.phone,
                    args.business.coordinates.latitude,
                    args.business.coordinates.longitude,
                    args.business.image_url, args.business.categories[0].title
                )
                bookmarkViewModel.insertData(bookmark)
            }

            override fun onSingleClick(v: View?) {
                firebaseLabelPhotos()
            }


        })
    }

    private fun callNumber(phoneNumber: String) {
        if (phoneNumber.isNotEmpty()) {
            val intent =
                Intent(
                    Intent.ACTION_DIAL,
                    Uri.fromParts("tel", phoneNumber, null)
                )
            startActivity(intent)
        }

    }

    //ML for labeling photos
    private fun firebaseLabelPhotos() {
        val SDK_INT = Build.VERSION.SDK_INT
        if (SDK_INT > 8) {
            val policy = StrictMode.ThreadPolicy.Builder()
                .permitAll().build()
            StrictMode.setThreadPolicy(policy)

            //firebase
            try {

                val image = InputImage.fromBitmap(getImageBitmap(args.business.image_url)!!, 0)
                val labeler = ImageLabeling.getClient(ImageLabelerOptions.DEFAULT_OPTIONS)
                labeler.process(image)
                    .addOnSuccessListener { labels ->
                        for (label in labels) {
                            val text = label.text
                            val confidence = label.confidence
                            val index = label.index
                            //snackbar to show the label of the photo
                            val snackbar = TSnackbar.make(
                                requireView(),
                                " $text",
                                TSnackbar.LENGTH_LONG
                            )
                            snackbar.setActionTextColor(Color.WHITE)
                            val snackbarView = snackbar.view
                            snackbarView.setBackgroundColor(Color.parseColor(getString(R.string.green_app)))
                            val textView =
                                snackbarView.findViewById(com.androidadvance.topsnackbar.R.id.snackbar_text) as TextView
                            textView.setTextColor(Color.WHITE)
                            snackbar.show()
                        }

                    }
                    .addOnFailureListener { e ->
                        Toast.makeText(context, "No result found", Toast.LENGTH_SHORT)
                            .show()
                        print(e)

                    }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    //converting url to bitmap
    private fun getImageBitmap(url: String): Bitmap? {
        var bm: Bitmap? = null
        try {
            val aURL = URL(url)
            val conn: URLConnection = aURL.openConnection()
            conn.connect()
            val `is`: InputStream = conn.getInputStream()
            val bis = BufferedInputStream(`is`)
            bm = BitmapFactory.decodeStream(bis)
            bis.close()
            `is`.close()
        } catch (e: IOException) {
            Log.e(TAG, "Error getting bitmap", e)
        }
        return bm
    }


}

