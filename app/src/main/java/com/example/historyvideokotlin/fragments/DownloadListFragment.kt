package com.example.historyvideokotlin.fragments

import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import com.example.historyvideokotlin.R
import com.example.historyvideokotlin.adapters.DownloadVideoAdapter
import com.example.historyvideokotlin.adapters.DownloadVideoListListener
import com.example.historyvideokotlin.base.BaseFragment
import com.example.historyvideokotlin.databinding.FragmentDownloadVideoBinding
import com.example.historyvideokotlin.model.DownloadVideo
import com.example.historyvideokotlin.utils.HistoryUtils
import com.example.historyvideokotlin.utils.MyLog
import com.example.historyvideokotlin.viewmodels.DownloadListViewModel

class DownloadListFragment :
    BaseFragment<DownloadListViewModel, FragmentDownloadVideoBinding>(),
    DownloadVideoListListener {

    private lateinit var adapter: DownloadVideoAdapter

    override fun getLayoutId() = R.layout.fragment_download_video

    override fun getViewModel() =
        ViewModelProvider(requireActivity()).get(DownloadListViewModel::class.java)

    override fun initData() {
        val myVideoType = arguments?.getInt(MY_VIDEO_TYPE_KEY)

        viewModel.videoList.observe(viewLifecycleOwner, { videoLists ->
            MyLog.e("downloadvideolist>>>>>>>>>>>>", videoLists.toString())
            if (videoLists.isNotEmpty()) {
                adapter = DownloadVideoAdapter(videoLists, requireContext(), this)
                binding.recylerVideo.adapter = adapter
            } else {
                binding.recylerVideo.visibility = GONE
                binding.tvEmptyList.visibility = VISIBLE
            }
        })
    }

    override fun onItemClick(video: DownloadVideo) {
        pushFragment(
            DownloadDetailFragment.newInstance(video),
            HistoryUtils.getSlideTransitionAnimationOptions()
        )
    }

    override fun onResume() {
        super.onResume()
        hideBottomMenu()
    }

    override fun onMore() {
    }

    companion object {
        const val MY_VIDEO_TYPE_KEY = "MY_VIDEO_TYPE_KEY"

        fun newInstance(myVideoType: Int) = DownloadListFragment().apply {
            arguments = bundleOf(
                MY_VIDEO_TYPE_KEY to myVideoType
            )
        }
    }
}
