package com.example.historyvideokotlin.fragments

import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import com.example.historyvideokotlin.R
import com.example.historyvideokotlin.base.BaseFragment
import com.example.historyvideokotlin.databinding.FragmentUserInfoBinding
import com.example.historyvideokotlin.model.MyVideoListData
import com.example.historyvideokotlin.model.MyVideoType
import com.example.historyvideokotlin.model.User
import com.example.historyvideokotlin.utils.HistoryUtils
import com.example.historyvideokotlin.viewmodels.UserInfoViewModel

class UserInfoFragment : BaseFragment<UserInfoViewModel, FragmentUserInfoBinding>() {

    private lateinit var myVideoListData: MyVideoListData
    private var userInfo: User? = null

    override fun getLayoutId(): Int {
        return R.layout.fragment_user_info
    }

    override fun getViewModel(): UserInfoViewModel =
        ViewModelProvider(requireActivity()).get(UserInfoViewModel::class.java)

    

    override fun initData() {
        viewModel.getMyVideoData()

//        viewModel.userList.observe(this, { data ->
//            data.let {
//                var user = it[0]
//                if (!user.user_image.isNullOrEmpty()) {
//                    Glide.with(requireContext()).load(user.user_image).into(binding.civUserAvatar)
//                }
//
//                binding.tvEmail.text = user.email
//            }
//        })
        viewModel.userInfo.observe(this, { data ->
            data.let {
                userInfo = it
            }
        })
        setItemClick()

//        viewModel.myVideoList.observe(this, { data ->
//            data.let {
//                myVideoListData = MyVideoListData(it)
//                setItemClick(myVideoListData)
//            }
//        })
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
                    MyVideoFragment.newInstance(MyVideoType.LIKE.id),
                    HistoryUtils.getSlideTransitionAnimationOptions()
                )
            }
            tvLater.setOnClickListener {
                pushFragment(
                    MyVideoFragment.newInstance(MyVideoType.LATER.id),
                    HistoryUtils.getSlideTransitionAnimationOptions()
                )
            }
            tvDownload.setOnClickListener {
                pushFragment(
                    DownloadListFragment.newInstance(MyVideoType.DOWNLOAD.id),
                    HistoryUtils.getSlideTransitionAnimationOptions()
                )
            }
            tvViewed.setOnClickListener {
                pushFragment(
                    MyVideoFragment.newInstance(MyVideoType.VIEW.id),
                    HistoryUtils.getSlideTransitionAnimationOptions()
                )
            }
        }
    }

    

    companion object {
        @JvmStatic
        fun newInstance() =
            UserInfoFragment().apply {
                arguments = bundleOf()
            }
    }
}
