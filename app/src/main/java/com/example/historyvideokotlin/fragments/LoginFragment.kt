package com.example.historyvideokotlin.fragments

import androidx.lifecycle.ViewModelProvider
import com.example.historyvideokotlin.R
import com.example.historyvideokotlin.base.AppEvent
import com.example.historyvideokotlin.base.BaseFragment
import com.example.historyvideokotlin.databinding.FragmentLoginBinding
import com.example.historyvideokotlin.viewmodels.LoginViewModel
import java.util.*

class LoginFragment : BaseFragment<LoginViewModel,FragmentLoginBinding>() {
    override fun getLayoutId(): Int {
        return R.layout.fragment_login
    }

    override fun getViewModel(): LoginViewModel =
        ViewModelProvider(requireActivity()).get(LoginViewModel::class.java)

    override fun getAnalyticsScreenName(): String? = null

    override fun initData() {

    }

    override fun onAppEvent(event: AppEvent<String, Objects>) {
    }
}