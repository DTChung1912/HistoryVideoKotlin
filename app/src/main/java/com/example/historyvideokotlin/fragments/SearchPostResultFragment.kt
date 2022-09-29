package com.example.historyvideokotlin.fragments

import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.historyvideokotlin.R
import com.example.historyvideokotlin.adapters.PostListAdapter
import com.example.historyvideokotlin.base.AppEvent
import com.example.historyvideokotlin.base.BaseFragment
import com.example.historyvideokotlin.databinding.FragmentSearchPostResultBinding
import com.example.historyvideokotlin.dialogfragments.PostMoreDialogFragment
import com.example.historyvideokotlin.model.Post
import com.example.historyvideokotlin.utils.HistoryUtils
import com.example.historyvideokotlin.viewmodels.SearchPostResultViewModel
import java.util.*

class SearchPostResultFragment :
    BaseFragment<SearchPostResultViewModel, FragmentSearchPostResultBinding>(),
    PostListAdapter.OnItemClickListener,
    PostMoreDialogFragment.OnItemClickListener {

    private var adapter: PostListAdapter? = null

    override fun getLayoutId(): Int {
        return R.layout.fragment_search_post_result
    }

    override fun getViewModel(): SearchPostResultViewModel =
        ViewModelProvider(requireActivity()).get(SearchPostResultViewModel::class.java)

    

    override fun initData() {
        val keyword = arguments?.getString(KEYWORD_KEY).toString()
        viewModel.getSeacrhPost(keyword)
        viewModel.postList.observe(this, { data ->
            data.let {
                setRecyclerView(it)
            }
        })
        binding.run {
            ivBack.setOnClickListener {
                popFragments(2,HistoryUtils.getSlideTransitionAnimationOptions())
            }
        }
    }

    private fun setRecyclerView(postList: List<Post>) {
        val linearLayoutManager = LinearLayoutManager(view?.context)
        adapter = PostListAdapter(postList, requireContext(), this)
        binding.recyclerSearchPost.setHasFixedSize(true)
        binding.recyclerSearchPost.layoutManager = linearLayoutManager
        binding.recyclerSearchPost.adapter = adapter
    }

    

    companion object {

        const val KEYWORD_KEY = "KEYWORD_KEY"

        fun newInstance(keyword: String) =
            SearchPostResultFragment().apply {
                arguments = bundleOf(
                    KEYWORD_KEY to keyword
                )
            }
    }

    override fun onItemClick(postListData: Post) {
        pushFragment(PostDetailFragment.newInstance(postListData),HistoryUtils.getSlideTransitionAnimationOptions())
    }

    override fun onMore(postId: Int) {

    }

    override fun onLater(postId: Int) {

    }

    override fun onDownload(postId: Int) {

    }

    override fun onShare(postId: Int) {

    }

    override fun onDontCare(postId: Int) {

    }
}