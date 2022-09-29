package com.example.historyvideokotlin.fragments

import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import com.example.historyvideokotlin.R
import com.example.historyvideokotlin.base.AppEvent
import com.example.historyvideokotlin.base.BaseFragment
import com.example.historyvideokotlin.databinding.FragmentVideoDetailBinding
import com.example.historyvideokotlin.model.Video
import com.example.historyvideokotlin.utils.HistoryUtils
import com.example.historyvideokotlin.utils.HistoryUtils.convertMillieToHMmSs
import com.example.historyvideokotlin.utils.MyLog
import com.example.historyvideokotlin.viewmodels.VideoDetailViewModel
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.drm.DrmSessionManagerProvider
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.upstream.LoadErrorHandlingPolicy
import java.util.*

class VideoDetailFragment :
    BaseFragment<VideoDetailViewModel, FragmentVideoDetailBinding>(),
    MediaSource.Factory {

    private lateinit var video: Video
    private var player: ExoPlayer? = null

    override fun getLayoutId(): Int = R.layout.fragment_video_detail

    override fun getViewModel(): VideoDetailViewModel =
        ViewModelProvider(requireActivity()).get(VideoDetailViewModel::class.java)

    

    override fun initData() {
        player = ExoPlayer.Builder(requireContext()).build()
        video = arguments?.getSerializable(VIDEO_KEY) as Video
        viewModel.getMyVideo(video.video_id)

        showFragment(
            R.id.fragmentContainerVideoDetail,
            VideoInfoFragment.newInstance(video),
            false,
            null
        )

        if (video.video_url.isNotEmpty()) {
            video.video_url.let { setUpPlayer(it) }
        }
        binding.ivBack.setOnClickListener {
            popFragment(HistoryUtils.getSlideTransitionAnimationOptions())
        }
    }

    private fun setUpPlayer(url: String) {
        binding.playerView.player = player

        val mediaItem = MediaItem.fromUri(url)
        player!!.setMediaItem(mediaItem)
        player!!.prepare()
        player!!.play()
        player!!.addListener(object : Player.Listener {
            override fun onPlaybackStateChanged(playbackState: Int) {
                if (playbackState == Player.STATE_READY) {
                    val duration: Long = player!!.duration
                    MyLog.e("timeInMillis", convertMillieToHMmSs(duration))
                }
                if (playbackState == Player.COMMAND_PLAY_PAUSE) {
                    val currentPosition = player!!.currentPosition
                    MyLog.e("currentPosition", convertMillieToHMmSs(currentPosition))
                }
            }
        })
    }


    override fun onPause() {
        super.onPause()
        if (player != null) {
            player?.release()
        }
    }

    override fun onResume() {
        super.onResume()
        hideBottomMenu()
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
}
