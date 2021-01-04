package com.seosh.simpleweather.view.adapter

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.seosh.simpleweather.utils.GlideUtils

@BindingAdapter("bindImage")
fun bindImage(view: ImageView, url: String?) {
    if(!url.isNullOrBlank()) {
        GlideUtils.setImageWithURL(view, url)
    }
}

@BindingAdapter("bindRes")
fun bindRes(view: ImageView, res: Int) {
    view.setImageResource(res)
}