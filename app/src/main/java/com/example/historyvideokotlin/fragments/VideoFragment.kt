package com.example.historyvideokotlin.fragments

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.historyvideokotlin.R
import com.example.historyvideokotlin.activities.DetailActivity
import com.example.historyvideokotlin.adapters.VideoAdapter
import com.example.historyvideokotlin.base.AppEvent
import com.example.historyvideokotlin.base.BaseFragment
import com.example.historyvideokotlin.databinding.FragmentVideoBinding
import com.example.historyvideokotlin.model.Video
import com.example.historyvideokotlin.utils.Constants.DETAIL_KEY
import com.example.historyvideokotlin.utils.Constants.VIDEO_DATA_KEY
import com.example.historyvideokotlin.utils.Constants.VIDEO_DETAIl_KEY
import com.example.historyvideokotlin.viewmodels.VideoViewModel
import java.util.*

class VideoFragment : BaseFragment<VideoViewModel, FragmentVideoBinding>(),
    VideoAdapter.OnItemClickListener {

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
                adapter = VideoAdapter(data, requireContext(), this)
                setRecyclerView(data)
            }
        })
    }

    private fun setRecyclerView(videoList: List<Video>) {
        val linearLayoutManager = LinearLayoutManager(view?.context)
        binding.myRecyclerView.setHasFixedSize(true)
        binding.myRecyclerView.layoutManager = linearLayoutManager
        binding.myRecyclerView.adapter = adapter
    }

    override fun onAppEvent(event: AppEvent<String, Objects>) {
    }

    override fun onItemClick(video: Video) {
        val intent = Intent(requireActivity(), DetailActivity::class.java)
        intent.putExtra(DETAIL_KEY, VIDEO_DETAIl_KEY)
        intent.putExtra(VIDEO_DATA_KEY, video)
        requireActivity().startActivity(intent)
//        pushFragment(VideoDetailFragment.newInstance(video), Utils.getSlideTransitionAnimationOptions())
    }
}