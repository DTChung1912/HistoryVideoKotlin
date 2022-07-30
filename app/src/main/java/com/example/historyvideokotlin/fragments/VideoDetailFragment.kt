package com.example.historyvideokotlin.fragments

import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.historyvideokotlin.R
import com.example.historyvideokotlin.base.AppEvent
import com.example.historyvideokotlin.base.BaseFragment
import com.example.historyvideokotlin.databinding.FragmentVideoDetailBinding
import com.example.historyvideokotlin.model.Video
import com.example.historyvideokotlin.viewmodels.VideoDetailViewModel
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.drm.DrmSessionManagerProvider
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.upstream.LoadErrorHandlingPolicy
import com.google.android.material.tabs.TabLayoutMediator
import java.util.*


class VideoDetailFragment : BaseFragment<VideoDetailViewModel,FragmentVideoDetailBinding>(), MediaSource.Factory {

    private var video : Video? = null
    private var player : ExoPlayer? = null

    companion object {
        const val VIDEO_KEY = "VIDEO_KEY"

        @JvmStatic
        fun newInstance(video: Video) =
            VideoDetailFragment().apply {
                arguments = bundleOf( VIDEO_KEY to video)
            }
    }

    override fun getLayoutId(): Int = R.layout.fragment_video_detail

    override fun getViewModel(): VideoDetailViewModel =
        ViewModelProvider(requireActivity()).get(VideoDetailViewModel::class.java)

    override fun getAnalyticsScreenName(): String? = null

    override fun initData() {
        video = arguments?.getSerializable(VIDEO_KEY) as Video
        showFragment(R.id.fragmentContainerVideoDetail, VideoInfoFragment.newInstance(video!!), false, null)

//        val titleIds = viewModel.getTabTitleIds()
//        binding.run {
//            tvVideoTitle.text = video?.title
//            tvViewCount.text = video?.view_count.toString() + " luot xem"
//            tvCreaterName.text = video?.creater
//
//            viewPager.adapter = PagerAdapter(this@VideoDetailFragment, video!!.video_id, "0", this@VideoDetailFragment)
//        }
        if (!video?.video_url.isNullOrEmpty()) {
            video?.video_url?.let { setUpPlayer(it) }
        }
        binding.ivBack.setOnClickListener {
            requireActivity().finish()
        }

//        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
//            tab.text = getString(titleIds[position])
//        }.attach()
    }

    private fun setUpPlayer(url : String) {
        player = ExoPlayer.Builder(requireContext()).build()
        binding.playerView.player = player

        val mediaItem = MediaItem.fromUri(url)

        player!!.setMediaItem(mediaItem)
        player!!.prepare()
        player!!.play()
    }

    override fun onPause() {
        super.onPause()
        if (player != null) {
            player?.release()
        }
    }

    override fun onAppEvent(event: AppEvent<String, Objects>) {
    }

//    private class PagerAdapter(fragment: Fragment,val video_id: String, val theme_id : String, val onItemClickListener: CommentFragment.OnItemClickListener) : FragmentStateAdapter(fragment) {
//        override fun getItemCount(): Int = 2
//
//        override fun createFragment(position: Int): Fragment {
//            return when (position) {
//                0 -> NextVideoFragment.newInstance(theme_id)
//                else -> CommentFragment.newInstance(video_id, onItemClickListener)
//            }
//        }
//    }

    override fun setDrmSessionManagerProvider(drmSessionManagerProvider: DrmSessionManagerProvider?): MediaSource.Factory {
        TODO("Not yet implemented")
    }

    override fun setLoadErrorHandlingPolicy(loadErrorHandlingPolicy: LoadErrorHandlingPolicy?): MediaSource.Factory {
        TODO("Not yet implemented")
    }

    override fun getSupportedTypes(): IntArray {
        TODO("Not yet implemented")
    }

    override fun createMediaSource(mediaItem: MediaItem): MediaSource {
        TODO("Not yet implemented")
    }

//    override fun onItemClick(comment_id: String) {
//        replaceFragment(R.id.fragmentContainerVideoDetail, CommentDetailFragment.newInstance(comment_id), false, null)
//    }
}