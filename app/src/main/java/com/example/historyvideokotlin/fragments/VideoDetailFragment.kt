package com.example.historyvideokotlin.fragments

import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import com.example.historyvideokotlin.R
import com.example.historyvideokotlin.base.AppEvent
import com.example.historyvideokotlin.base.BaseFragment
import com.example.historyvideokotlin.databinding.FragmentVideoDetailBinding
import com.example.historyvideokotlin.model.Video
import com.example.historyvideokotlin.utils.HistoryUtils
import com.example.historyvideokotlin.viewmodels.VideoDetailViewModel
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.drm.DrmSessionManagerProvider
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.upstream.LoadErrorHandlingPolicy
import java.util.*

class VideoDetailFragment : BaseFragment<VideoDetailViewModel, FragmentVideoDetailBinding>(),
    MediaSource.Factory {

    private var player: ExoPlayer? = null

    companion object {
        const val VIDEO_KEY = "VIDEO_KEY"

        @JvmStatic
        fun newInstance(video: Video) =
            VideoDetailFragment().apply {
                arguments = bundleOf(
                    VIDEO_KEY to video
                )
            }
    }

    override fun getLayoutId(): Int = R.layout.fragment_video_detail

    override fun getViewModel(): VideoDetailViewModel =
        ViewModelProvider(requireActivity()).get(VideoDetailViewModel::class.java)

    override fun getAnalyticsScreenName(): String? = null

    override fun initData() {
        val video = arguments?.getSerializable(VIDEO_KEY) as Video
        showFragment(
            R.id.fragmentContainerVideoDetail,
            VideoInfoFragment.newInstance(video),
            false,
            null
        )

        if (!video?.video_url.isNullOrEmpty()) {
            video?.video_url?.let { setUpPlayer(it) }
        }
        binding.ivBack.setOnClickListener {
            popFragment(HistoryUtils.getSlideTransitionAnimationOptions())
//            requireActivity().finish()
        }

    }

    private fun setUpPlayer(url: String) {
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

    override fun onResume() {
        super.onResume()
        showBottomMenu(false)
    }

    override fun onAppEvent(event: AppEvent<String, Objects>) {
    }

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
}