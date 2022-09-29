package com.example.historyvideokotlin.fragments

import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.historyvideokotlin.R
import com.example.historyvideokotlin.adapters.NextVideoAdapter
import com.example.historyvideokotlin.base.AppEvent
import com.example.historyvideokotlin.base.BaseFragment
import com.example.historyvideokotlin.databinding.FragmentNextVideoBinding
import com.example.historyvideokotlin.dialogfragments.VideoMoreDialogFragment
import com.example.historyvideokotlin.model.Video
import com.example.historyvideokotlin.utils.HistoryUtils
import com.example.historyvideokotlin.viewmodels.NextVideoViewModel
import java.util.*

class NextVideoFragment : BaseFragment<NextVideoViewModel, FragmentNextVideoBinding>(),
    NextVideoAdapter.OnItemClickListener, VideoMoreDialogFragment.OnItemClickListener {

    private lateinit var adapter: NextVideoAdapter

    companion object {
        const val THEME_ID_KEY = "THEME_ID_KEY"

        @JvmStatic
        fun newInstance(theme_id: Int) =
            NextVideoFragment().apply {
                arguments = bundleOf(
                    THEME_ID_KEY to theme_id
                )
            }
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_next_video
    }

    override fun getViewModel(): NextVideoViewModel =
        ViewModelProvider(requireActivity()).get(NextVideoViewModel::class.java)

    

    override fun initData() {
        val theme_id = arguments?.getInt(THEME_ID_KEY)

        viewModel.getVideoData()
        viewModel.videoList.observe(this, { data ->
            data.let {
                setRecyclerView(it)
            }

        })
    }

    private fun setRecyclerView(videoList: List<Video>) {
        adapter = NextVideoAdapter(videoList,  requireContext(), this)
        val linearLayoutManager = LinearLayoutManager(view?.context)
        binding.myRecyclerView.setHasFixedSize(true)
        binding.myRecyclerView.layoutManager = linearLayoutManager
        binding.myRecyclerView.adapter = adapter
    }

    

    override fun onResume() {
        super.onResume()
        hideBottomMenu()
    }

    override fun onPlay(video: Video) {
        pushFragment(VideoDetailFragment.newInstance(video),HistoryUtils.getSlideTransitionAnimationOptions())
    }


    override fun onMore(videoId: Int) {
        VideoMoreDialogFragment.newInstance(videoId,this).show(parentFragmentManager,null)
    }

    override fun onLater(videoId: Int) {
        viewModel.updateLaterVideo( videoId, 1)
    }

    override fun onDownload(videoId: Int) {
        viewModel.updateDownloadVideo( videoId, 1)
    }

    override fun onShare(videoId: Int) {
        viewModel.updateShareVideo( videoId, 1)
    }

    override fun onDontCare(videoId: Int) {
        viewModel.updateDontCareVideo(videoId,1)
    }

}