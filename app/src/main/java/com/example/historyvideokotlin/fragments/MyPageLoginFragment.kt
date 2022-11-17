package com.example.historyvideokotlin.fragments

import androidx.lifecycle.ViewModelProvider
import com.example.historyvideokotlin.R
import com.example.historyvideokotlin.base.BaseFragment
import com.example.historyvideokotlin.databinding.FragmentMyPageLoginBinding
import com.example.historyvideokotlin.utils.HistoryUtils
import com.example.historyvideokotlin.viewmodels.MyPageLoginViewModel

class MyPageLoginFragment : BaseFragment<MyPageLoginViewModel, FragmentMyPageLoginBinding>() {
    override fun getLayoutId(): Int {
        return R.layout.fragment_my_page_login
    }

    override fun getViewModel(): MyPageLoginViewModel =
        ViewModelProvider(requireActivity()).get(MyPageLoginViewModel::class.java)

    override fun initData() {
        binding.run {
            btnLogin.setOnClickListener {
                pushFragment(
                    HistoryLoginFragment.newInstance(),
                    HistoryUtils.getSlideTransitionAnimationOptions()
                )
            }
            btnRegister.setOnClickListener {
                pushFragment(
                    RegisterFragment.newInstance(),
                    HistoryUtils.getSlideTransitionAnimationOptions()
                )
            }

            tvDownloadVideoList.setOnClickListener {
                pushFragment(
                    DownloadListFragment.newInstance(4),
                    HistoryUtils.getSlideTransitionAnimationOptions()
                )
            }

            tvDownloadPostList.setOnClickListener {
                pushFragment(
                    DownloadPostFragment.newInstance(),
                    HistoryUtils.getSlideTransitionAnimationOptions()
                )
            }
        }
    }

    companion object {

        fun newInstance() = MyPageLoginFragment()
    }
}
