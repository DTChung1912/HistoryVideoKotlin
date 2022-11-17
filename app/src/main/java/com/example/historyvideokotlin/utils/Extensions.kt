package com.example.historyvideokotlin.utils

import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.view.animation.TranslateAnimation
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

fun Fragment.setStatusBarColor(colorResId: Int) {
    activity?.apply {
        window?.statusBarColor = ContextCompat.getColor(this, colorResId)
    }
}

fun Fragment.finishFragmentOrActivity() {
    val fragmentManager = requireActivity().supportFragmentManager
    if (fragmentManager.backStackEntryCount >= 1) {
        if (isResumed) {
            fragmentManager.popBackStack()
        }
    } else {
        requireActivity().finish()
    }
}

fun View.setMargins(left: Int, top: Int, right: Int, bottom: Int) {
    if (layoutParams is ViewGroup.MarginLayoutParams) {
        val params = layoutParams as ViewGroup.MarginLayoutParams
        params.setMargins(left, top, right, bottom)
        requestLayout()
    }
}

fun View.slideUp() {
    visibility = GONE
    val animate = TranslateAnimation(
        0F, // fromXDelta
        0F, // toXDelta
        height.toFloat(), // fromYDelta
        0F
    ) // toYDelta
    animate.duration = 500
    animate.fillAfter = true
    startAnimation(animate)
}

fun View.slideDown() {
    visibility = VISIBLE
    val animate = TranslateAnimation(
        0F, // fromXDelta
        0F, // toXDelta
        0F, // fromYDelta
        height.toFloat()
    ) // toYDelta
    animate.duration = 500
    animate.fillAfter = true
    startAnimation(animate)
}
