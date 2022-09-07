package com.example.historyvideokotlin.fragments

import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.historyvideokotlin.R
import com.example.historyvideokotlin.base.AppEvent
import com.example.historyvideokotlin.base.BaseFragment
import com.example.historyvideokotlin.databinding.FragmentMyPageLoginBinding
import com.example.historyvideokotlin.utils.HistoryUtils
import com.example.historyvideokotlin.viewmodels.MyPageLoginViewModel
import java.util.*

class MyPageLoginFragment : BaseFragment<MyPageLoginViewModel, FragmentMyPageLoginBinding>() {
    override fun getLayoutId(): Int {
        return R.layout.fragment_my_page_login
    }

    override fun getViewModel(): MyPageLoginViewModel =
        ViewModelProvider(requireActivity()).get(MyPageLoginViewModel::class.java)

    override fun getAnalyticsScreenName(): String? = null

    override fun initData() {
        binding.run {
            btnLogin.setOnClickListener {
                pushFragment(
                    HistoryLoginFragment.newInstance(),
                    HistoryUtils.getSlideTransitionAnimationOptions()
                )
//                findNavController().navigate(R.id.action_myPageFragment_to_historyLoginFragment)
            }
            btnRegister.setOnClickListener {
                pushFragment(
                    RegisterFragment.newInstance(),
                    HistoryUtils.getSlideTransitionAnimationOptions()
                )
            }
        }
    }

    override fun onAppEvent(event: AppEvent<String, Objects>) {
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            MyPageLoginFragment()
    }
}