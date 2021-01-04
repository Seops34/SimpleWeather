package com.seosh.simpleweather.utils

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.seosh.simpleweather.R

object GlideUtils {

    fun setImageWithURL(view: ImageView, url: String) {
        val requestOptions = RequestOptions
            .diskCacheStrategyOf(DiskCacheStrategy.ALL)

        Glide.with(view.context)
            .load(url)
            .apply(requestOptions)
            .placeholder(R.drawable.ic_wait_image)
            .error(R.drawable.ic_error_image)
            .fallback(R.drawable.ic_no_image)
            .into(view)
    }
}