package com.example.historyvideokotlin.fragments

import android.view.inputmethod.EditorInfo
import android.widget.TextView.OnEditorActionListener
import androidx.lifecycle.ViewModelProvider
import com.example.historyvideokotlin.R
import com.example.historyvideokotlin.adapters.KeywordAdapter
import com.example.historyvideokotlin.adapters.VideoAdapter
import com.example.historyvideokotlin.base.BaseFragment
import com.example.historyvideokotlin.databinding.FragmentSearchVideoBinding
import com.example.historyvideokotlin.model.Keyword
import com.example.historyvideokotlin.model.Video
import com.example.historyvideokotlin.utils.HistoryUtils
import com.example.historyvideokotlin.viewmodels.SearchVideoViewModel

class SearchVideoFragment :
    BaseFragment<SearchVideoViewModel, FragmentSearchVideoBinding>() {

    override fun getLayoutId(): Int {
        return R.layout.fragment_search_video
    }

    override fun getViewModel(): SearchVideoViewModel =
        ViewModelProvider(requireActivity()).get(SearchVideoViewModel::class.java)

    override fun initData() {
        binding.viewModel = viewModel
        viewModel.setSearchVisible(true)
        viewModel.videoList.observe(this, { data ->
            data.let {
                binding.recyclerSearchVideo.adapter =
                    VideoAdapter(
                        it,
                        requireContext(),
                        object : VideoAdapter.ItemListener {
                            override fun onItemClick(video: Video) {
                                pushFragment(
                                    VideoDetailFragment.newInstance(video),
                                    HistoryUtils.getSlideNoAnimationOptions()
                                )
                            }

                            override fun onMore(videoId: Int) {
                            }
                        }
                    )
            }
        })

        viewModel.keywordList.observe(viewLifecycleOwner) {
            binding.recyclerKeyword.adapter =
                KeywordAdapter(
                    it,
                    object : KeywordAdapter.OnItemClickListener {
                        override fun onKeyword(keyword: Keyword) {
                            viewModel.setKeyword(keyword.content.toString())
                        }

                        override fun onDelete(keyword: Keyword) {
                            viewModel.deleteKeyword(keyword)
                            binding.edtSearch.setText("")
                        }

                        override fun onPaste(keyword: Keyword) {
                            binding.edtSearch.setText(keyword.content)
                        }
                    }
                )
        }

        binding.edtSearch.setOnClickListener {
            viewModel.setSearchVisible(true)
        }
        binding.edtSearch.setOnEditorActionListener(
            OnEditorActionListener { v, actionId, event ->
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    val keyword = Keyword(
                        keyword_id = null,
                        content = binding.edtSearch.text.toString(),
                        search_type = 1
                    )
                    viewModel.insertKeyword(keyword)
                    viewModel.setKeyword(binding.edtSearch.text.toString())
                    return@OnEditorActionListener true
                }
                false
            }
        )
        binding.ivBack.setOnClickListener {
            back()
        }
    }

    override fun onResume() {
        super.onResume()
        hideBottomMenu()
    }

    companion object {

        fun newInstance() = SearchVideoFragment()
    }
}
