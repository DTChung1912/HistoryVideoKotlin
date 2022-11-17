package com.example.historyvideokotlin.utils

import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.historyvideokotlin.R
import com.example.historyvideokotlin.model.AnswerType
import de.hdodenhof.circleimageview.CircleImageView

@BindingAdapter("imageResourceLike")
fun loadImageResource(imageView: ImageView, isLike: Boolean) {
    if (isLike) {
        imageView.setImageResource(R.drawable.ic_liked)
    } else {
        imageView.setImageResource(R.drawable.ic_like)
    }
}

@BindingAdapter("imageResourceDislike")
fun loadImageResourceDislike(imageView: ImageView, isDislike: Boolean) {
    if (isDislike) {
        imageView.setImageResource(R.drawable.ic_disliked)
    } else {
        imageView.setImageResource(R.drawable.ic_dislike)
    }
}

@BindingAdapter("imageUrl")
fun CircleImageView.loadImageUrl(url: String?) {
    if (url.isNullOrEmpty()) {
        setImageResource(R.drawable.ic_avatar)
    } else {
        Glide.with(context).load(url).into(this)
    }
}

@BindingAdapter("url")
fun ImageView.loadUrl(url: String?) {
    if (url.isNullOrEmpty()) {
        setImageResource(R.drawable.ic_avatar)
    } else {
        Glide.with(context).load(url).transform(CenterCrop(), RoundedCorners(10)).into(this)
    }
}

@BindingAdapter("isVisible")
fun View.setViewVisibility(isVisible: Boolean) {
    visibility = if (isVisible) VISIBLE else GONE
}

@BindingAdapter("correct", "select")
fun TextView.setViewColor(correct: String, select: String) {
    if (text.equals(select)) {
        if (select.equals(correct)) {
            setBackgroundResource(R.drawable.background_answer_choosed)
            setTextColor(ContextCompat.getColor(context, R.color.white))
        } else {
            setBackgroundResource(R.drawable.background_answer_wrong)
            setTextColor(ContextCompat.getColor(context, R.color.yellow))
        }
    }
}

@BindingAdapter("status")
fun TextView.setAnswerColor(status: Int) {
    when (status) {
        AnswerType.CORRECT.ordinal -> {
            setBackgroundResource(R.drawable.background_answer_choosed)
            setTextColor(ContextCompat.getColor(context, R.color.white))
        }
        AnswerType.WRONG.ordinal -> {
            setBackgroundResource(R.drawable.background_answer_wrong)
            setTextColor(ContextCompat.getColor(context, R.color.yellow))
        }
        AnswerType.NOT.ordinal -> {
            setBackgroundResource(R.drawable.background_answer)
            setTextColor(ContextCompat.getColor(context, R.color.gray_dark))
        }
    }
}

@BindingAdapter("textLike")
fun TextView.setTextLike(count: Int) {
    text = if (count > 0) count.toString() else "Thích"
}

@BindingAdapter("textDislike")
fun TextView.setTextDislike(count: Int) {
    text = if (count > 0) count.toString() else "Không Thích"
}

@BindingAdapter("isLike")
fun ImageView.setLikeVisibility(isLike: Int) {
    when (isLike) {
        1 -> {
            setImageResource(R.drawable.ic_liked)
        }
        else -> {
            setImageResource(R.drawable.ic_like)
        }
    }
}

@BindingAdapter("isDisike")
fun ImageView.setDislikeVisibility(isDisike: Int) {
    when (isDisike) {
        2 -> {
            setImageResource(R.drawable.ic_liked)
        }
        else -> {
            setImageResource(R.drawable.ic_like)
        }
    }
}
