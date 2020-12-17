package com.thanaa.restaurantweatherapp.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.thanaa.restaurantweatherapp.R
import com.thanaa.restaurantweatherapp.RestaurantModel.Businesses
import com.thanaa.restaurantweatherapp.viewmodel.YelpViewModel

class RestaurantAdapter(private val food: List<Businesses>) :
        RecyclerView.Adapter<RestaurantAdapter.ViewHolder>() {
    lateinit var viewModel: YelpViewModel

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val nameText: TextView = view.findViewById(R.id.name)
        private val addressText: TextView = view.findViewById(R.id.address)
        private val reviewsText: TextView = view.findViewById(R.id.reviews)
        private val priceText: TextView = view.findViewById(R.id.price)
        private val openText: TextView = view.findViewById(R.id.isOpen)
        private val categoryText: TextView = view.findViewById(R.id.category)
        private val ratingBar: RatingBar = view.findViewById(R.id.ratingBar)
        private val imageView: ImageView = view.findViewById(R.id.imageView)
        private val progressBar: ProgressBar = view.findViewById(R.id.progressBar)

        fun bind(foodItem: Businesses) {
            progressBar.visibility = View.VISIBLE
            nameText.text = foodItem.name
            addressText.text = foodItem.location.address1
            priceText.text = foodItem.price
            openText.text = if (foodItem.is_closed) "Closed" else "Open"
            categoryText.text = foodItem.categories[0].title
            ratingBar.rating = foodItem.rating.toFloat()
            reviewsText.text = "${foodItem.review_count} reviews"

            Glide.with(imageView)
                .load(foodItem.image_url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .apply(RequestOptions().transform(CenterCrop(), RoundedCorners(30)))
                    .into(imageView)
            progressBar.visibility = View.GONE

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.row_item, parent, false)
        )
    }

    override fun getItemCount(): Int = food.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        viewModel = YelpViewModel()
        val foodItem: Businesses = food[position]

        holder.bind(foodItem)
    }


}