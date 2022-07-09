package com.example.historyvideokotlin.fragments

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.historyvideokotlin.R
import com.example.historyvideokotlin.base.AppEvent
import com.example.historyvideokotlin.base.BaseFragment
import com.example.historyvideokotlin.databinding.FragmentCommentBinding
import com.example.historyvideokotlin.viewmodels.CommentViewModel
import java.util.*

class CommentFragment : BaseFragment<CommentViewModel, FragmentCommentBinding>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            CommentFragment()
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_comment
    }

    override fun getViewModel(): CommentViewModel =
        ViewModelProvider(requireActivity()).get(CommentViewModel::class.java)

    override fun getAnalyticsScreenName(): String? = null

    override fun initData() {
    }

    override fun onAppEvent(event: AppEvent<String, Objects>) {

    }
}