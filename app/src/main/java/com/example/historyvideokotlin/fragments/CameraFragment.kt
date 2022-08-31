package com.example.historyvideokotlin.fragments

import androidx.lifecycle.ViewModelProvider
import com.example.historyvideokotlin.R
import com.example.historyvideokotlin.base.AppEvent
import com.example.historyvideokotlin.base.BaseFragment
import com.example.historyvideokotlin.databinding.FragmentCameraBinding
import com.example.historyvideokotlin.viewmodels.CameraViewModel
import java.util.*

class CameraFragment : BaseFragment<CameraViewModel, FragmentCameraBinding>() {


    override fun getLayoutId(): Int {
        return R.layout.fragment_camera
    }

    override fun getViewModel(): CameraViewModel =
        ViewModelProvider(requireActivity()).get(CameraViewModel::class.java)

    override fun getAnalyticsScreenName(): String? = null

    override fun initData() {

    }

    override fun onAppEvent(event: AppEvent<String, Objects>) {
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            CameraFragment()

    }
}