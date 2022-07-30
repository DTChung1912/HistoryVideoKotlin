package com.example.historyvideokotlin.fragments

import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import com.example.historyvideokotlin.R
import com.example.historyvideokotlin.base.AppEvent
import com.example.historyvideokotlin.base.BaseFragment
import com.example.historyvideokotlin.databinding.FragmentUserInfoBinding
import com.example.historyvideokotlin.viewmodels.UserInfoViewModel
import java.util.*

class UserInfoFragment : BaseFragment<UserInfoViewModel, FragmentUserInfoBinding>() {

    private lateinit var userId : String

    override fun getLayoutId(): Int {
        return R.layout.fragment_user_info
    }

    override fun getViewModel(): UserInfoViewModel =
        ViewModelProvider(requireActivity()).get(UserInfoViewModel::class.java)

    override fun getAnalyticsScreenName(): String? = null

    override fun initData() {
        userId = arguments?.getString(USER_ID_KEY).toString()
    }

    override fun onAppEvent(event: AppEvent<String, Objects>) {
    }

    companion object {

        const val USER_ID_KEY = "USER_ID_KEY"

        @JvmStatic
        fun newInstance(userId: String) =
            UserInfoFragment().apply {
                arguments = bundleOf(
                    USER_ID_KEY to userId
                )
            }
    }
}