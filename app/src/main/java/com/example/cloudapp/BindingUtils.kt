package com.example.cloudapp

import android.widget.ImageView
import android.widget.ProgressBar
import androidx.databinding.BindingAdapter
import coil.load
import com.example.cloudapp.models.Product


@BindingAdapter("load_image")
fun ImageView.loadImage(url: String) {
    load(url)
}

@BindingAdapter("set_visibility")
fun ProgressBar.setVisibility(isVisible: Boolean) {
    visibility = if (isVisible) {
        ProgressBar.VISIBLE
    } else {
        ProgressBar.GONE
    }
}
