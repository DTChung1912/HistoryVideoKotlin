package com.example.historyvideokotlin.fragments

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.historyvideokotlin.R
import com.example.historyvideokotlin.viewmodels.VideoViewModel
import com.example.historyvideokotlin.base.AppEvent
import com.example.historyvideokotlin.base.BaseFragment
import com.example.historyvideokotlin.databinding.FragmentVideoBinding
import java.util.*

class VideoFragment : BaseFragment<VideoViewModel, FragmentVideoBinding>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    companion object {
                @JvmStatic
        fun newInstance() =
            VideoFragment()
    }

    override fun getLayoutId(): Int = R.layout.fragment_video

    override fun getViewModel(): VideoViewModel =
        ViewModelProvider(requireActivity()).get(VideoViewModel::class.java)

    override fun getAnalyticsScreenName(): String? = null

    override fun initData() {
    }

    override fun onAppEvent(event: AppEvent<String, Objects>) {
    }
}