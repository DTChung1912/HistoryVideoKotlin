package com.example.historyvideokotlin.fragments

import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.historyvideokotlin.R
import com.example.historyvideokotlin.repository.HistoryUserManager
import com.example.historyvideokotlin.base.AppEvent
import com.example.historyvideokotlin.base.BaseFragment
import com.example.historyvideokotlin.databinding.FragmentVideoInfoBinding
import com.example.historyvideokotlin.dialogfragments.LoginDialogFragment
import com.example.historyvideokotlin.dialogfragments.OnLoginItemClickListener
import com.example.historyvideokotlin.model.Video
import com.example.historyvideokotlin.utils.HistoryUtils
import com.example.historyvideokotlin.viewmodels.VideoInfoViewModel
import com.google.android.material.tabs.TabLayoutMediator
import java.util.*

class VideoInfoFragment : BaseFragment<VideoInfoViewModel, FragmentVideoInfoBinding>() {
//    private var video: Video? = null

    private var isLikeCheck = false
    private var isDislikeCheck = false

    override fun getLayoutId(): Int {
        return R.layout.fragment_video_info
    }

    override fun getViewModel(): VideoInfoViewModel =
        ViewModelProvider(requireActivity()).get(VideoInfoViewModel::class.java)

    override fun getAnalyticsScreenName(): String? = null

    override fun initData() {
//        var isLike = -1
        val video = arguments?.getSerializable(VIDEO_KEY) as Video
        val titleIds = viewModel.getTabTitleIds()

        var myVideoId = 0

        viewModel.updateViewedMyVideo( video.video_id)
        viewModel.updateViewCountVideo(video.video_id)
        viewModel.getMyVideo(video.video_id)
        viewModel.myVideoList.observe(this, { data ->
            data.let {

                if (it[0].isLike != null) {
                    var isLike = it[0].isLike
                    myVideoId = it[0].my_video_id
                    if (isLike == 1) {
                        isLikeCheck = true
                        binding.ivLike.setImageResource(R.drawable.liked)
                    } else {
                        isDislikeCheck = true
                        binding.ivDislike.setImageResource(R.drawable.disliked)
                    }
                } else {
                    binding.ivLike.setImageResource(R.drawable.like_video)
                    binding.ivDislike.setImageResource(R.drawable.dislike_video)
                }
            }
        })

        binding.run {

            tvVideoTitle.text = video.title
            tvViewCount.text = video.view_count.toString() + " luot xem"
            tvCreaterName.text = video.creater
            tvDateSubmitted.text = video.date_submitted

            viewPager.adapter =
                PagerAdapter(
                    this@VideoInfoFragment,
                    video.video_id,
                    video.theme_id
                )

            if (!HistoryUserManager.checkUserLogined()) {
                ivLike.setOnClickListener {
                    showLoginDialog()
                }

                ivDislike.setOnClickListener {
                    showLoginDialog()
                }

                ivDownload.setOnClickListener {
                    showLoginDialog()
                }

                ivSave.setOnClickListener {
                    showLoginDialog()
                }
            } else {
                ivLike.setOnClickListener {
                    isLikeCheck = true
                    isDislikeCheck = false
                    ivDislike.setImageResource(R.drawable.dislike_video)
                    viewModel.updateLikeMyVideo(video.video_id)
                }

                ivDislike.setOnClickListener {
                    isLikeCheck = false
                    isDislikeCheck = true

                    ivLike.setImageResource(R.drawable.like_video)
                    viewModel.deleteLikeMyVideo(myVideoId)
                }

                ivDownload.setOnClickListener {
                    viewModel.updateDownloadMyVideo( video.video_id)
                }

                ivSave.setOnClickListener {
                    viewModel.updateLaterMyVideo(video.video_id)
                }
            }
        }

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = getString(titleIds[position])
        }.attach()
    }

    private fun showLoginDialog() {
        LoginDialogFragment.newInstance(object : OnLoginItemClickListener {
            override fun onLogin() {
                pushFragment(HistoryLoginFragment.newInstance(), HistoryUtils.getSlideTransitionAnimationOptions())
            }

            override fun onRegister() {
                pushFragment(RegisterFragment.newInstance(), HistoryUtils.getSlideTransitionAnimationOptions())
            }

        }).show(parentFragmentManager,null)
    }

    override fun onResume() {
        super.onResume()
        showBottomMenu(false)
    }

    private class PagerAdapter(
        fragment: Fragment,
        val video_id: Int,
        val theme_id: Int
    ) :
        FragmentStateAdapter(fragment) {
        override fun getItemCount(): Int = 2

        override fun createFragment(position: Int): Fragment {
            return when (position) {
                0 -> NextVideoFragment.newInstance(theme_id)
                else -> CommentFragment.newInstance(video_id)
            }
        }
    }

    override fun onAppEvent(event: AppEvent<String, Objects>) {
    }

    companion object {
        const val VIDEO_KEY = "VIDEO_KEY"
        @JvmStatic
        fun newInstance(video: Video) =
            VideoInfoFragment().apply {
                arguments = bundleOf(
                    VIDEO_KEY to video
                )
            }
    }
}
