package com.example.historyvideokotlin.utils

import com.example.historyvideokotlin.R
import com.ncapdevi.fragnav.FragNavTransactionOptions

object Utils {

    fun getSlideTransitionAnimationOptions(): FragNavTransactionOptions {
        return FragNavTransactionOptions.Builder()
            .allowReordering(true)
            .customAnimations(
                R.anim.slide_in_from_right,
                R.anim.slide_out_to_left,
                R.anim.slide_in_from_left,
                R.anim.slide_out_to_right
            )
            .build()
    }
}