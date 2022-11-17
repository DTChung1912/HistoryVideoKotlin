package com.example.historyvideokotlin.fragments

import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import com.example.historyvideokotlin.R
import com.example.historyvideokotlin.base.BaseFragment
import com.example.historyvideokotlin.databinding.FragmentUserInfoBinding
import com.example.historyvideokotlin.model.MyVideoType
import com.example.historyvideokotlin.model.User
import com.example.historyvideokotlin.utils.HistoryUtils
import com.example.historyvideokotlin.viewmodels.UserInfoViewModel

class UserInfoFragment : BaseFragment<UserInfoViewModel, FragmentUserInfoBinding>() {

    private var userInfo: User? = null

    override fun getLayoutId(): Int {
        return R.layout.fragment_user_info
    }

    override fun getViewModel(): UserInfoViewModel =
        ViewModelProvider(requireActivity()).get(UserInfoViewModel::class.java)

    override fun initData() {
        binding.viewModel = viewModel
        viewModel.getUserInfo()
        viewModel.user.observe(viewLifecycleOwner) {
            userInfo = it
        }
        setItemClick()
    }

    private fun setItemClick() {
        binding.run {
            ivEdit.setOnClickListener {
                pushFragment(
                    UserEditFragment.newInstance(userInfo!!),
                    HistoryUtils.getSlideTransitionAnimationOptions()
                )
            }
            tvLike.setOnClickListener {
                pushFragment(
                    MyVideoFragment.newInstance(MyVideoType.LIKE.ordinal),
                    HistoryUtils.getSlideTransitionAnimationOptions()
                )
            }
            tvLater.setOnClickListener {
                pushFragment(
                    MyVideoFragment.newInstance(MyVideoType.LATER.ordinal),
                    HistoryUtils.getSlideTransitionAnimationOptions()
                )
            }
            tvDownload.setOnClickListener {
                pushFragment(
                    DownloadListFragment.newInstance(MyVideoType.DOWNLOAD.ordinal),
                    HistoryUtils.getSlideTransitionAnimationOptions()
                )
            }
            tvViewed.setOnClickListener {
                pushFragment(
                    MyVideoFragment.newInstance(MyVideoType.VIEW.ordinal),
                    HistoryUtils.getSlideTransitionAnimationOptions()
                )
            }
            tvViewedPost.setOnClickListener {
                pushFragment(
                    MyPostFragment.newInstance(),
                    HistoryUtils.getSlideTransitionAnimationOptions()
                )
            }
            tvDownloadPost.setOnClickListener {
                pushFragment(
                    DownloadPostFragment.newInstance(),
                    HistoryUtils.getSlideTransitionAnimationOptions()
                )
            }
        }
    }

    companion object {
        fun newInstance() =
            UserInfoFragment().apply {
                arguments = bundleOf()
            }
    }
}
