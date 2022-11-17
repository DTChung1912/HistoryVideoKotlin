package com.example.historyvideokotlin.fragments

import androidx.lifecycle.ViewModelProvider
import com.example.historyvideokotlin.R
import com.example.historyvideokotlin.base.BaseFragment
import com.example.historyvideokotlin.databinding.FragmentAdminBinding
import com.example.historyvideokotlin.utils.HistoryUtils
import com.example.historyvideokotlin.viewmodels.AdminViewModel

class AdminFragment : BaseFragment<AdminViewModel, FragmentAdminBinding>() {
    override fun getLayoutId() = R.layout.fragment_admin

    override fun getViewModel() =
        ViewModelProvider(requireActivity()).get(AdminViewModel::class.java)

    override fun initData() {
        binding.rlPost.setOnClickListener {
            pushFragment(
                PostManagementFragment.newInstance(),
                HistoryUtils.getSlideNoAnimationOptions()
            )
        }

        binding.rlQuiz.setOnClickListener {
            pushFragment(
                QuizManagementFragment.newInstance(),
                HistoryUtils.getSlideNoAnimationOptions()
            )
        }

        binding.rlUser.setOnClickListener {
            pushFragment(
                UserManagementFragment.newInstance(),
                HistoryUtils.getSlideNoAnimationOptions()
            )
        }

        binding.rlTheme.setOnClickListener {
            pushFragment(
                ThemeManagementFragment.newInstance(),
                HistoryUtils.getSlideNoAnimationOptions()
            )
        }

        binding.rlVideo.setOnClickListener {
            pushFragment(
                VideoManagementFragment.newInstance(),
                HistoryUtils.getSlideNoAnimationOptions()
            )
        }
    }

    companion object {
        fun newInstance() = AdminFragment()
    }
}
