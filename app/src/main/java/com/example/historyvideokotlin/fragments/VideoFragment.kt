package com.example.historyvideokotlin.fragments

import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.historyvideokotlin.R
import com.example.historyvideokotlin.activities.MainActivity
import com.example.historyvideokotlin.adapters.VideoAdapter
import com.example.historyvideokotlin.base.AppEvent
import com.example.historyvideokotlin.base.BaseFragment
import com.example.historyvideokotlin.databinding.FragmentVideoBinding
import com.example.historyvideokotlin.dialogfragments.VideoMoreDialogFragment
import com.example.historyvideokotlin.model.Video
import com.example.historyvideokotlin.utils.HistoryUtils
import com.example.historyvideokotlin.viewmodels.VideoViewModel
import java.util.*

class VideoFragment : BaseFragment<VideoViewModel, FragmentVideoBinding>(),
    VideoAdapter.OnItemClickListener, VideoMoreDialogFragment.OnItemClickListener {

    private var adapter: VideoAdapter? = null

    companion object {

        @JvmStatic
        fun newInstance() =
            VideoFragment()
    }

    override fun getLayoutId(): Int = R.layout.fragment_video

    override fun getViewModel(): VideoViewModel =
        ViewModelProvider(requireActivity()).get(VideoViewModel::class.java)

    override fun getAnalyticsScreenName(): String? = null

    override fun initData() {
        viewModel.getVideoData()
        viewModel.videoList.observe(this, { data ->
            data.let {
                adapter = VideoAdapter(it, requireContext(), this)
                setRecyclerView(it)
            }
        })
        binding.run {
            toolbar.tvTitle.text = "Video"
            toolbar.ivSearch.setOnClickListener {
                (activity as? MainActivity)?.onSearchClick()
            }
            toolbar.ivMenu.setOnClickListener {
                (activity as? MainActivity)?.onSettingClick()
            }
        }
    }

    private fun setRecyclerView(videoList: List<Video>) {
        val linearLayoutManager = LinearLayoutManager(view?.context)
        binding.myRecyclerView.setHasFixedSize(true)
        binding.myRecyclerView.layoutManager = linearLayoutManager
        binding.myRecyclerView.adapter = adapter
    }

    override fun onAppEvent(event: AppEvent<String, Objects>) {
    }

    override fun onResume() {
        super.onResume()
        showBottomMenu(true)
    }

    override fun onItemClick(video: Video) {

        pushFragment(
            VideoDetailFragment.newInstance(video),
            HistoryUtils.getSlideTransitionAnimationOptions()
        )
//        nextFragment(R.id.action_videoFragment_to_videoDetailFragment,VideoDetailFragment.newInstance(video))
    }

    override fun onMore(videoId: Int) {
        VideoMoreDialogFragment.newInstance(videoId, this).show(parentFragmentManager, null)
    }

    override fun onLater(videoId: Int) {
//        viewModel.updateLaterVideo(videoId, 1)
    }

    override fun onDownload(videoId: Int) {
//        viewModel.updateDownloadMyVideo(videoId, 1)
        viewModel.updateVideoDownload(videoId)
    }

    override fun onShare(videoId: Int) {
//        viewModel.updateShareVideo(videoId, 1)
    }

    override fun onDontCare(videoId: Int) {
//        viewModel.updateDontCareVideo(videoId, 1)
    }
}