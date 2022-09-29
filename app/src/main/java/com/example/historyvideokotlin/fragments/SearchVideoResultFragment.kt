package com.example.historyvideokotlin.fragments

import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.historyvideokotlin.R
import com.example.historyvideokotlin.adapters.PostListAdapter
import com.example.historyvideokotlin.adapters.VideoAdapter
import com.example.historyvideokotlin.base.AppEvent
import com.example.historyvideokotlin.base.BaseFragment
import com.example.historyvideokotlin.databinding.FragmentSearchVideoResultBinding
import com.example.historyvideokotlin.model.Post
import com.example.historyvideokotlin.model.Video
import com.example.historyvideokotlin.utils.HistoryUtils
import com.example.historyvideokotlin.viewmodels.SearchVideoResultViewModel
import java.util.*

class SearchVideoResultFragment :
    BaseFragment<SearchVideoResultViewModel, FragmentSearchVideoResultBinding>(),
    VideoAdapter.OnItemClickListener {

    private var adapter: VideoAdapter? = null

    override fun getLayoutId(): Int {
        return R.layout.fragment_search_video_result
    }

    override fun getViewModel(): SearchVideoResultViewModel =
        ViewModelProvider(requireActivity()).get(SearchVideoResultViewModel::class.java)

    

    override fun initData() {
        val keyword = arguments?.getString(KEYWORD_KEY).toString()
        viewModel.getSeacrhVideo(keyword)
        viewModel.videoList.observe(this, { data ->
            data.let {
//                setRecyclerView(it)
            }
        })

        binding.run {
            ivBack.setOnClickListener {
                popFragments(2,HistoryUtils.getSlideTransitionAnimationOptions())
            }
        }
    }

//    private fun setRecyclerView(videoList: List<Video>) {
//        val linearLayoutManager = LinearLayoutManager(view?.context)
//        adapter = VideoAdapter(videoList, requireContext(), this)
//        binding.recyclerSearchVideo.setHasFixedSize(true)
//        binding.recyclerSearchVideo.layoutManager = linearLayoutManager
//        binding.recyclerSearchVideo.adapter = adapter
//    }

    

    companion object {

        const val KEYWORD_KEY = "KEYWORD_KEY"

        @JvmStatic
        fun newInstance(keyword: String) =
            SearchVideoResultFragment().apply {
                arguments = bundleOf(
                    KEYWORD_KEY to keyword
                )
            }
    }

    override fun onItemClick(video: Video) {

    }

    override fun onMore(videoId: Int) {

    }
}