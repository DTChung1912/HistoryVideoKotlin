package com.example.historyvideokotlin.fragments

import androidx.lifecycle.ViewModelProvider
import com.example.historyvideokotlin.R
import com.example.historyvideokotlin.base.AppEvent
import com.example.historyvideokotlin.base.BaseFragment
import com.example.historyvideokotlin.databinding.FragmentRegisterBinding
import com.example.historyvideokotlin.viewmodels.RegisterViewModel
import java.util.*

class RegisterFragment : BaseFragment<RegisterViewModel,FragmentRegisterBinding>(){
    override fun getLayoutId(): Int {
        return R.layout.fragment_register
    }

    override fun getViewModel(): RegisterViewModel =
        ViewModelProvider(requireActivity()).get(RegisterViewModel::class.java)

    override fun getAnalyticsScreenName(): String? = null

    override fun initData() {
    }

    override fun onAppEvent(event: AppEvent<String, Objects>) {
    }

}