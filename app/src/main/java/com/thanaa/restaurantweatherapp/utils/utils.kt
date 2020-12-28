package com.thanaa.restaurantweatherapp.utils

import android.content.Context
import android.widget.ImageView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.request.RequestOptions
import com.thanaa.restaurantweatherapp.R

fun getProgressDrawable(context: Context): CircularProgressDrawable {
    //progress than will be displayed in an image
    return CircularProgressDrawable(context).apply {
        strokeWidth = 10f
        centerRadius = 50f
        start()
    }


}

fun ImageView.loadImage(progressDrawable: CircularProgressDrawable, url: String) {
    val options = RequestOptions().placeholder(progressDrawable).error(R.drawable.no_image)
    Glide.with(this.context)
        .setDefaultRequestOptions(options)
        .load(url)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .centerCrop()
        .apply(RequestOptions().transform(CenterCrop()))
        .into(this)


}
