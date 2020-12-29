package com.thanaa.restaurantweatherapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.thanaa.restaurantweatherapp.R
import com.thanaa.restaurantweatherapp.model.Bookmark
import com.thanaa.restaurantweatherapp.ui.BookmarkFragmentDirections

class BookmarkAdapter(private val bookmark: List<Bookmark>, private val fragmentID: Int) :
    RecyclerView.Adapter<BookmarkAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val name: TextView = view.findViewById(R.id.restaurantName)
        private val category: TextView = view.findViewById(R.id.category)
        private val imageView: ImageView = view.findViewById(R.id.imageView)
        fun bind(bookmark: Bookmark, holder: ViewHolder, fragmentID: Int) {

            name.text = bookmark.name
            category.text = bookmark.category
            Glide.with(imageView)
                .load("${bookmark.image_url}")
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .into(imageView)


            if (fragmentID == 1) {
                //when user clicks on item it navigate to the map
                imageView.setOnClickListener {
                    val action =
                        BookmarkFragmentDirections.actionBookmarkFragmentToLocationFragment(
                            bookmark.latitude.toString(),
                            bookmark.longitude.toString(),
                            bookmark.name
                        )
                    holder.itemView.findNavController().navigate(action)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.bookmark_row, parent, false)
        )
    }

    override fun getItemCount(): Int = bookmark.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val bookmarkItem = bookmark[position]
        holder.bind(bookmarkItem, holder, fragmentID)

    }


}