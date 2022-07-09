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
import com.example.historyvideokotlin.databinding.FragmentNextVideoBinding
import com.example.historyvideokotlin.viewmodels.NextVideoViewModel
import java.util.*

class NextVideoFragment : BaseFragment<NextVideoViewModel, FragmentNextVideoBinding>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            NextVideoFragment()
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_next_video
    }

    override fun getViewModel(): NextVideoViewModel =
        ViewModelProvider(requireActivity()).get(NextVideoViewModel::class.java)

    override fun getAnalyticsScreenName(): String? = null

    override fun initData() {
    }

    override fun onAppEvent(event: AppEvent<String, Objects>) {
    }
}