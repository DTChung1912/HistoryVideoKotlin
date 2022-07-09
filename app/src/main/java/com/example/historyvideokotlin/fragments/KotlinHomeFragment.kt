package com.example.historyvideokotlin.fragments

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.historyvideokotlin.R
import com.example.historyvideokotlin.viewmodels.KotlinHomeViewModel
import com.example.historyvideokotlin.base.AppEvent
import com.example.historyvideokotlin.base.BaseFragment
import com.example.historyvideokotlin.databinding.FragmentKotlinHomeBinding
import java.util.*

class KotlinHomeFragment : BaseFragment<KotlinHomeViewModel, FragmentKotlinHomeBinding>() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            KotlinHomeFragment()
    }

    override fun getLayoutId(): Int = R.layout.fragment_kotlin_home

    override fun getViewModel(): KotlinHomeViewModel =
        ViewModelProvider(requireActivity()).get(KotlinHomeViewModel::class.java)

    override fun getAnalyticsScreenName(): String? = null

    override fun initData() {
    }

    override fun onAppEvent(event: AppEvent<String, Objects>) {
    }
}