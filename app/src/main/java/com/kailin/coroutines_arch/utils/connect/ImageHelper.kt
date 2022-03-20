package com.kailin.coroutines_arch.utils.connect

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions

object ImageHelper {

    private val options = RequestOptions()
        .fitCenter()
        .priority(Priority.HIGH)
        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)

    fun load(imageView: ImageView, url: String) {
        Glide.with(imageView)
            .applyDefaultRequestOptions(options)
            .load(url)
            .into(imageView)
    }
}