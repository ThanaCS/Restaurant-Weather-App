package com.thanaa.restaurantweatherapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.RatingBar
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.thanaa.restaurantweatherapp.R
import com.thanaa.restaurantweatherapp.model.Businesses
import com.thanaa.restaurantweatherapp.ui.HistoryFragmentDirections
import com.thanaa.restaurantweatherapp.ui.HomeFragmentDirections
import com.thanaa.restaurantweatherapp.utils.getProgressDrawable
import com.thanaa.restaurantweatherapp.utils.loadImage


const val TAG = "RestaurantAdapter"

class RestaurantAdapter(private var food: List<Businesses>, private val fragmentID: Int) :
    RecyclerView.Adapter<RestaurantAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val nameText: TextView = view.findViewById(R.id.restaurantName)

        private val reviewsText: TextView = view.findViewById(R.id.reviews)
        private val priceText: TextView = view.findViewById(R.id.price)
        private val categoryText: TextView = view.findViewById(R.id.category)
        private val ratingBar: RatingBar = view.findViewById(R.id.ratingBar)
        private val imageView: ImageView = view.findViewById(R.id.imageView)
        private val progressBar: ProgressBar = view.findViewById(R.id.progressBar)
        private val progressDrawable = getProgressDrawable(view.context)

        fun bind(foodItem: Businesses, FragmentID: Int) {

            imageView.visibility = View.GONE
            nameText.text = foodItem.name
            priceText.text = foodItem.price
            categoryText.text = foodItem.categories[0].title
            ratingBar.rating = foodItem.rating.toFloat()
            reviewsText.text = "${foodItem.review_count} reviews"
            imageView.loadImage(progressDrawable, foodItem.image_url)
            progressBar.visibility = View.GONE
            imageView.visibility = View.VISIBLE

            if (FragmentID == 1)
            //passing a restaurant to InfoFragment and navigating & passing data to history
                imageView.setOnClickListener {
                    val action =
                        HomeFragmentDirections.actionHomeFragmentToInfoFragment(foodItem)
                    imageView.findNavController().navigate(action)

                }
            if (FragmentID == 2)
            //passing a restaurant to InfoFragment and navigating & passing data to history
                imageView.setOnClickListener {
                    val action =
                        HistoryFragmentDirections.actionHistoryFragmentToInfoFragment(foodItem)
                    imageView.findNavController().navigate(action)

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
        holder.bind(foodItem, fragmentID)

    }

    //notify the recycler view about these changes
    fun setData(food: List<Businesses>) {
        this.food = food
        notifyDataSetChanged()
    }


}