package com.example.historyvideokotlin.fragments

import androidx.lifecycle.ViewModelProvider
import com.example.historyvideokotlin.R
import com.example.historyvideokotlin.viewmodels.MyPageViewModel
import com.example.historyvideokotlin.base.AppEvent
import com.example.historyvideokotlin.base.BaseFragment
import com.example.historyvideokotlin.databinding.FragmentMyPageBinding
import java.util.*

class MyPageFragment : BaseFragment<MyPageViewModel, FragmentMyPageBinding>() {
    override fun getLayoutId(): Int = R.layout.fragment_my_page

    override fun getViewModel(): MyPageViewModel =
        ViewModelProvider(requireActivity()).get(MyPageViewModel::class.java)

    override fun getAnalyticsScreenName(): String? = null

    override fun initData() {
        binding.viewmodels = viewModel

        viewModel.refreshUser()

        viewModel.user.observe(this, { data ->
            data?.let {
                binding.tvName.text = data.get(0).user_name
            }
        })
    }

    override fun onAppEvent(event: AppEvent<String, Objects>) {

    }

    companion object {
        @JvmStatic
        fun newInstance() = MyPageFragment()
    }

}