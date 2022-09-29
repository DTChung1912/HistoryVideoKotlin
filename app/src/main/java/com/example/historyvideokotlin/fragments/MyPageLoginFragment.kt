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
//                findNavController().navigate(R.id.action_myPageFragment_to_historyLoginFragment)
            }
            btnRegister.setOnClickListener {
                pushFragment(
                    RegisterFragment.newInstance(),
                    HistoryUtils.getSlideTransitionAnimationOptions()
                )
            }

            tvDownloadVideoList.setOnClickListener {
                pushFragment(DownloadListFragment.newInstance(4),HistoryUtils.getSlideTransitionAnimationOptions())
            }
        }
    }

    

    companion object {

        @JvmStatic
        fun newInstance() =
            MyPageLoginFragment()
    }
}