package com.example.historyvideokotlin.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.historyvideokotlin.R
import com.example.historyvideokotlin.base.AppEvent
import com.example.historyvideokotlin.base.BaseFragment
import com.example.historyvideokotlin.databinding.FragmentPostDetailBinding
import com.example.historyvideokotlin.viewmodels.MyPageViewModel
import com.example.historyvideokotlin.viewmodels.PostDetailViewModel
import java.util.*

class PostDetailFragment : BaseFragment<PostDetailViewModel,FragmentPostDetailBinding>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            PostDetailFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }

    override fun getLayoutId(): Int = R.layout.fragment_post_detail

    override fun getViewModel(): PostDetailViewModel =
        ViewModelProvider(requireActivity()).get(PostDetailViewModel::class.java)

    override fun getAnalyticsScreenName(): String? = null

    override fun initData() {
    }

    override fun onAppEvent(event: AppEvent<String, Objects>) {
    }
}