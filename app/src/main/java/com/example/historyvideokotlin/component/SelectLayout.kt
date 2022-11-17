package com.example.historyvideokotlin.component

import android.widget.LinearLayout
import android.widget.TextView
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import com.example.historyvideokotlin.R

class SelectLayout : LinearLayout, View.OnClickListener {
    private var currentTextView: TextView? = null

    constructor(context: Context?) : super(context) {
        this.orientation = VERTICAL
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        this.orientation = VERTICAL
    }

    override fun onClick(v: View) {
        val textView: TextView = v as TextView
        if (currentTextView != null) {
            currentTextView?.background = resources.getDrawable(R.drawable.background_answer)
            currentTextView?.setTextColor(resources.getColor(R.color.gray_dark))
        }
        textView.background = resources.getDrawable(R.drawable.background_answer_choosed)
        textView.setTextColor(resources.getColor(R.color.white))
        currentTextView = textView
    }

    override fun addView(
        child: View,
        index: Int,
        params: ViewGroup.LayoutParams?
    ) {
        super.addView(child, index, params)
        setChildrenOnClickListener(child as TextView)
    }

    override fun addView(child: View, params: ViewGroup.LayoutParams?) {
        super.addView(child, params)
        setChildrenOnClickListener(child as TextView)
    }

    private fun setChildrenOnClickListener(textView: TextView) {
        textView.setOnClickListener(this)
    }
}
