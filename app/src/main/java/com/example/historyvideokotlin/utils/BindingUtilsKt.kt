package com.example.historyvideokotlin.utils

import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.example.historyvideokotlin.R

@androidx.databinding.BindingAdapter("imageResourceLike")
fun loadImageResource(imageView: ImageView , isLike : Boolean) {
    if (isLike) {
        imageView.setImageResource(R.drawable.liked)
    } else {
        imageView.setImageResource(R.drawable.like_video)
    }
}

@androidx.databinding.BindingAdapter("imageResourceDislike")
fun loadImageResourceDislike(imageView: ImageView , isLike : Boolean) {
    if (isLike) {
        imageView.setImageResource(R.drawable.dislike_video)
    } else {
        imageView.setImageResource(R.drawable.disliked)
    }
}


