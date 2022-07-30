package com.example.historyvideokotlin.fragments

import android.annotation.SuppressLint
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.historyvideokotlin.R
import com.example.historyvideokotlin.base.AppEvent
import com.example.historyvideokotlin.base.BaseFragment
import com.example.historyvideokotlin.databinding.FragmentVideoInfoBinding
import com.example.historyvideokotlin.model.Video
import com.example.historyvideokotlin.viewmodels.VideoInfoViewModel
import com.google.android.material.tabs.TabLayoutMediator
import java.util.*


class VideoInfoFragment : BaseFragment<VideoInfoViewModel, FragmentVideoInfoBinding>(),
    View.OnClickListener {
    private var video: Video? = null
    private var isLike = false
    private var isDislike = false

    override fun getLayoutId(): Int {
        return R.layout.fragment_video_info
    }

    override fun getViewModel(): VideoInfoViewModel =
        ViewModelProvider(requireActivity()).get(VideoInfoViewModel::class.java)

    override fun getAnalyticsScreenName(): String? = null

    override fun initData() {
        video = arguments?.getSerializable(VideoDetailFragment.VIDEO_KEY) as Video
        val titleIds = viewModel.getTabTitleIds()
        binding.run {
            tvVideoTitle.text = video?.title
            tvViewCount.text = video?.view_count.toString() + " luot xem"
            tvCreaterName.text = video?.creater

            viewPager.adapter = PagerAdapter(this@VideoInfoFragment, video!!.video_id, "0")
        }

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = getString(titleIds[position])
        }.attach()
        setClick()
    }

    private class PagerAdapter(fragment: Fragment, val video_id: String, val theme_id: String) :
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

        @JvmStatic
        fun newInstance(video: Video) =
            VideoInfoFragment().apply {
                arguments = bundleOf(VideoDetailFragment.VIDEO_KEY to video)
            }
    }

    private fun setClick() {
        binding.run {
            ivLike.setOnClickListener(this@VideoInfoFragment)
            ivDislike.setOnClickListener(this@VideoInfoFragment)
        }
    }

    override fun onClick(v: View?) {
        if (v != null) {
            when (v.id) {
                R.id.ivLike -> {
                    if (isLike) {
                        binding.setIsLiked(false)
                        isLike = false
                    } else {
                        binding.setIsLiked(true)
                        isLike = true
                    }
                }
                R.id.ivDislike -> {
                  if (isDislike) {
                      binding.setIsDisliked(false)
                      isDislike = false
                  } else {
                      binding.setIsDisliked(true)
                      isDislike = true
                  }
                }
                R.id.ivDownload -> {

                }
                R.id.ivSave -> {

                }
                R.id.ivExtend -> {

                }
            }
        }
    }
}
