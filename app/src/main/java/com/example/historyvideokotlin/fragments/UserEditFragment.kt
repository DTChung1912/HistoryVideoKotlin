package com.example.historyvideokotlin.fragments

import androidx.lifecycle.ViewModelProvider
import com.example.historyvideokotlin.R
import com.example.historyvideokotlin.base.AppEvent
import com.example.historyvideokotlin.base.BaseFragment
import com.example.historyvideokotlin.databinding.FragmentUserEditBinding
import com.example.historyvideokotlin.utils.HistoryUtils
import com.example.historyvideokotlin.viewmodels.UserEditViewModel
import java.util.*

class UserEditFragment : BaseFragment<UserEditViewModel, FragmentUserEditBinding>() {

    override fun getLayoutId(): Int {
        return R.layout.fragment_user_edit
    }

    override fun getViewModel(): UserEditViewModel =
        ViewModelProvider(requireActivity()).get(UserEditViewModel::class.java)

    override fun getAnalyticsScreenName(): String? = null

    override fun initData() {
    }

    override fun onAppEvent(event: AppEvent<String, Objects>) {
        binding.run {
            ivBack.setOnClickListener {
                popFragment(HistoryUtils.getSlideTransitionAnimationOptions())
            }
        }
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            UserEditFragment()
    }
}