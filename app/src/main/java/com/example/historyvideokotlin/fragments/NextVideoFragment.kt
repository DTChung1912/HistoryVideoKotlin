package com.example.historyvideokotlin.fragments

import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import com.example.historyvideokotlin.R
import com.example.historyvideokotlin.adapters.NextVideoAdapter
import com.example.historyvideokotlin.base.BaseFragment
import com.example.historyvideokotlin.databinding.FragmentNextVideoBinding
import com.example.historyvideokotlin.dialogfragments.VideoMoreDialogFragment
import com.example.historyvideokotlin.model.Video
import com.example.historyvideokotlin.utils.HistoryUtils
import com.example.historyvideokotlin.viewmodels.NextVideoViewModel
import java.util.*

class NextVideoFragment :
    BaseFragment<NextVideoViewModel, FragmentNextVideoBinding>(),
    NextVideoAdapter.OnItemClickListener,
    VideoMoreDialogFragment.OnItemClickListener {

    private lateinit var adapter: NextVideoAdapter

    override fun getLayoutId(): Int {
        return R.layout.fragment_next_video
    }

    override fun getViewModel(): NextVideoViewModel =
        ViewModelProvider(requireActivity()).get(NextVideoViewModel::class.java)

    override fun initData() {
        val theme_id = arguments?.getInt(THEME_ID_KEY)
        val videoId = arguments?.getInt(VIDEO_ID_KEY, 0)

        viewModel.getVideoData()
        viewModel.getNextVideo(videoId!!)
        viewModel.nextVideoList.observe(this, { data ->
            data.let {
                adapter = NextVideoAdapter(it, requireContext(), this)
                binding.myRecyclerView.adapter = adapter
            }
        })
    }

    override fun onResume() {
        super.onResume()
        hideBottomMenu()
    }

    override fun onPlay(video: Video) {
        pushFragment(
            VideoDetailFragment.newInstance(video),
            HistoryUtils.getSlideTransitionAnimationOptions()
        )
    }

    override fun onMore(videoId: Int) {
        VideoMoreDialogFragment.newInstance(videoId, this).show(parentFragmentManager, null)
    }

    override fun onLater(videoId: Int) {
    }

    override fun onDownload(videoId: Int) {
    }

    override fun onShare(videoId: Int) {
    }

    override fun onDontCare(videoId: Int) {
    }

    companion object {
        const val THEME_ID_KEY = "THEME_ID_KEY"
        const val VIDEO_ID_KEY = "VIDEO_ID_KEY"

        @JvmStatic
        fun newInstance(videoId: Int, theme_id: Int) =
            NextVideoFragment().apply {
                arguments = bundleOf(
                    THEME_ID_KEY to theme_id,
                    VIDEO_ID_KEY to videoId
                )
            }
    }
}
