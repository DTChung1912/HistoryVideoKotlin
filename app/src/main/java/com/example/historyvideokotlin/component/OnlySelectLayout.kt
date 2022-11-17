package com.example.historyvideokotlin.component

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import com.example.historyvideokotlin.R

class OnlySelectLayout : LinearLayout, View.OnClickListener {
    private var currentImageView: ImageView? = null

    constructor(context: Context?) : super(context) {
        this.orientation = VERTICAL
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        this.orientation = VERTICAL
    }

    override fun onClick(v: View) {
        val textView: ImageView = v as ImageView
        if (currentImageView != null) {
            currentImageView?.setImageResource(R.drawable.background_answer)
        }
        textView.setImageResource(R.drawable.background_answer_choosed)
        currentImageView = textView
    }

    override fun addView(
        child: View,
        index: Int,
        params: ViewGroup.LayoutParams?
    ) {
        super.addView(child, index, params)
        setChildrenOnClickListener(child as ImageView)
    }

    override fun addView(child: View, params: ViewGroup.LayoutParams?) {
        super.addView(child, params)
        setChildrenOnClickListener(child as ImageView)
    }

    private fun setChildrenOnClickListener(textView: ImageView) {
        textView.setOnClickListener(this)
    }
}
