package com.example.projectforworktesting.mock.ui

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.core.widget.ImageViewCompat
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso

@BindingAdapter("app:imgUri", "app:error", "app:placeholder")
fun imageViewState(imageView: ImageView, uri: String, error: Drawable, placeholder: Drawable)
{
    if (uri.isNotEmpty()){
        Picasso.get().load(uri)
            .placeholder(placeholder)
            .error(error)
            .into(imageView)
    }

}

