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
import com.example.historyvideokotlin.databinding.FragmentMyPostBinding
import com.example.historyvideokotlin.viewmodels.MyPostViewModel
import java.util.*

class MyPostFragment : BaseFragment<MyPostViewModel,FragmentMyPostBinding>() {

    override fun getLayoutId(): Int {
        return R.layout.fragment_my_post
    }

    override fun getViewModel(): MyPostViewModel =
        ViewModelProvider(requireActivity()).get(MyPostViewModel::class.java)

    override fun getAnalyticsScreenName(): String? = null

    override fun initData() {
    }

    override fun onAppEvent(event: AppEvent<String, Objects>) {
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MyPostFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}