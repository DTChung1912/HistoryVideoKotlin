package com.example.historyvideokotlin.fragments

import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import com.example.historyvideokotlin.R
import com.example.historyvideokotlin.base.BaseFragment
import com.example.historyvideokotlin.databinding.FragmentDownloadDetailBinding
import com.example.historyvideokotlin.model.DownloadVideo
import com.example.historyvideokotlin.utils.HistoryUtils
import com.example.historyvideokotlin.viewmodels.DownloadDetailViewModel
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.drm.DrmSessionManagerProvider
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.upstream.LoadErrorHandlingPolicy

class DownloadDetailFragment :
    BaseFragment<DownloadDetailViewModel, FragmentDownloadDetailBinding>(),
    MediaSource.Factory {

    private var player: ExoPlayer? = null

    override fun getLayoutId() = R.layout.fragment_download_detail

    override fun getViewModel() =
        ViewModelProvider(requireActivity()).get(DownloadDetailViewModel::class.java)

    override fun initData() {
        player = ExoPlayer.Builder(requireContext()).build()
        val video = arguments?.getSerializable(DOWNLOAD_VIDEO_KEY) as DownloadVideo

        if (video.video_url!!.isNotEmpty()) {
            video.video_url.let { setUpPlayer(it!!) }
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

        const val DOWNLOAD_VIDEO_KEY = "DOWNLOAD_VIDEO_KEY"

        fun newInstance(video: DownloadVideo) = DownloadDetailFragment().apply {
            arguments = bundleOf(
                DOWNLOAD_VIDEO_KEY to video
            )
        }
    }
}