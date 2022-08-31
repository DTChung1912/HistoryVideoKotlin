package com.example.historyvideokotlin.fragments

import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.historyvideokotlin.R
import com.example.historyvideokotlin.adapters.PostListAdapter
import com.example.historyvideokotlin.base.AppEvent
import com.example.historyvideokotlin.base.BaseFragment
import com.example.historyvideokotlin.databinding.FragmentPostListBinding
import com.example.historyvideokotlin.model.Post
import com.example.historyvideokotlin.viewmodels.PostViewModel
import com.ncapdevi.fragnav.FragNavTransactionOptions
import java.util.*

class PostListFragment(val postList: List<Post>) :
    BaseFragment<PostViewModel, FragmentPostListBinding>(),
    PostListAdapter.OnItemClickListener {
    private var postType: Int = 0
    private var adapter: PostListAdapter? = null

    override fun getLayoutId(): Int {
        return R.layout.fragment_post_list
    }

    override fun getViewModel(): PostViewModel =
        ViewModelProvider(requireActivity()).get(PostViewModel::class.java)

    override fun getAnalyticsScreenName(): String? = null

    override fun initData() {
        postType = arguments?.getInt(KEY_POST_LIST_TYPE)!!

        var postDataList = mutableListOf<Post>()
        postDataList.clear()

        for (i in 0 until postList.size) {
            if (postType == postList[i].post_type_id.toInt() - 1) {
                postDataList.add(postList[i])
            }
        }
        setRecyclerView(postDataList)
    }

    private fun setRecyclerView(postList: List<Post>) {
        val linearLayoutManager = LinearLayoutManager(view?.context)
        adapter = PostListAdapter(postList, requireContext(), this)
        binding.recyclerViewPost.setHasFixedSize(true)
        binding.recyclerViewPost.layoutManager = linearLayoutManager
        binding.recyclerViewPost.adapter = adapter
    }

    override fun onAppEvent(event: AppEvent<String, Objects>) {
    }

    companion object {

        const val KEY_POST_LIST_TYPE = "KEY_POST_LIST_TYPE"

        @JvmStatic
        fun newInstance(postType: Int, postList: List<Post>) =
            PostListFragment(postList).apply {
                arguments = bundleOf(
                    KEY_POST_LIST_TYPE to postType
                )
            }
    }

    override fun onItemClick(post: Post) {
        pushFragment(
            PostDetailFragment.newInstance(post),
            FragNavTransactionOptions.Builder().allowReordering(true).build()
        )
    }
}