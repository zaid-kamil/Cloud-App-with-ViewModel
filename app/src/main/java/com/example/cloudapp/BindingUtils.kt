package com.example.cloudapp

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.load
import com.example.cloudapp.models.Product


@BindingAdapter("load_image")
fun ImageView.loadImage(url: String) {
    load(url)
}
