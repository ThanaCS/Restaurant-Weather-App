package com.thanaa.restaurantweatherapp.ui.homeFragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.RatingBar
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.thanaa.restaurantweatherapp.R
import com.thanaa.restaurantweatherapp.model.Businesses
import kotlinx.android.synthetic.main.row_item.view.*

const val TAG = "RestaurantAdapter"
class RestaurantAdapter(private val food: List<Businesses>) :
    RecyclerView.Adapter<RestaurantAdapter.ViewHolder>() {


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

        fun bind(foodItem: Businesses, holder: ViewHolder) {
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
                .apply(RequestOptions().transform(CenterCrop(), RoundedCorners(10)))
                .into(imageView)
            progressBar.visibility = View.GONE

            //passing a restaurant to InfoFragment and navigating & passing data to history
            holder.itemView.restaurant_row.setOnClickListener {

                val actionPassToInfo =
                    HomeFragmentDirections.actionHomeFragmentToInfoFragment(foodItem)
                holder.itemView.imageView.findNavController().navigate(actionPassToInfo)


            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.row_item, parent, false)
        )
    }

    override fun getItemCount(): Int = food.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val foodItem: Businesses = food[position]
        holder.bind(foodItem, holder)


    }


}