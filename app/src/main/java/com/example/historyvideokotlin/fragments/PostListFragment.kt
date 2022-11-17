package com.example.historyvideokotlin.fragments

import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import com.example.historyvideokotlin.R
import com.example.historyvideokotlin.adapters.PostListAdapter
import com.example.historyvideokotlin.base.BaseFragment
import com.example.historyvideokotlin.databinding.FragmentPostListBinding
import com.example.historyvideokotlin.dialogfragments.PostMoreDialogFragment
import com.example.historyvideokotlin.model.Post
import com.example.historyvideokotlin.viewmodels.PostViewModel
import com.ncapdevi.fragnav.FragNavTransactionOptions
import java.util.*

class PostListFragment(val postList: List<Post>) :
    BaseFragment<PostViewModel, FragmentPostListBinding>(),
    PostListAdapter.OnItemClickListener,
    PostMoreDialogFragment.OnItemClickListener {
    private var postType: Int = 0
    private var adapter: PostListAdapter? = null

    override fun getLayoutId(): Int {
        return R.layout.fragment_post_list
    }

    override fun getViewModel(): PostViewModel =
        ViewModelProvider(requireActivity()).get(PostViewModel::class.java)

    override fun initData() {
        postType = arguments?.getInt(KEY_POST_LIST_TYPE)!!

        var postDataList = mutableListOf<Post>()
        postDataList.clear()

        for (i in 0 until postList.size) {
            if (postType == postList[i].post_type_id - 1) {
                postDataList.add(postList[i])
            }
        }
        adapter = PostListAdapter(postDataList, requireContext(), this)
        binding.recyclerViewPost.adapter = adapter
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
//        nextFragment(PostDetailFragment.newInstance(post))
//        findNavController().navigate(
//            R.id.action_postFragment_to_postDetailFragment,
//            bundleOf(Pair(POST_DATA_KEY, post))
//        )
    }

    override fun onMore(postId: Int) {
        PostMoreDialogFragment.newInstance(postId, this).show(parentFragmentManager, null)
    }

    override fun onLater(postId: Int) {
    }

    override fun onDownload(postId: Int) {
        viewModel.updatePostDownload(postId)
    }

    override fun onShare(postId: Int) {
    }

    override fun onDontCare(postId: Int) {
    }
}
