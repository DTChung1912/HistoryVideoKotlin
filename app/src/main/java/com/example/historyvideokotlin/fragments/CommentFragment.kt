package com.example.historyvideokotlin.fragments

import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.historyvideokotlin.R
import com.example.historyvideokotlin.adapters.CommentAdapter
import com.example.historyvideokotlin.base.AppEvent
import com.example.historyvideokotlin.base.BaseFragment
import com.example.historyvideokotlin.databinding.FragmentCommentBinding
import com.example.historyvideokotlin.model.Comment
import com.example.historyvideokotlin.model.User
import com.example.historyvideokotlin.utils.Utils
import com.example.historyvideokotlin.viewmodels.CommentViewModel
import java.util.*
import kotlin.collections.ArrayList

class CommentFragment : BaseFragment<CommentViewModel, FragmentCommentBinding>(), CommentAdapter.OnItemClickListener {

    var adapter : CommentAdapter? = null
    var videoId = "0"

    override fun getLayoutId(): Int {
        return R.layout.fragment_comment
    }

    override fun getViewModel(): CommentViewModel =
        ViewModelProvider(requireActivity()).get(CommentViewModel::class.java)

    override fun getAnalyticsScreenName(): String? = null

    override fun initData() {
        videoId = arguments?.getString(VIDEO_ID_KEY).toString()
        viewModel.getCommentData(videoId)
        viewModel.commentList.observe(this, {data ->
            data.let {
                setRecyclerView(it)
            }
        })
    }

    private fun setRecyclerView(commentList: List<Comment>) {
        val linearLayoutManager = LinearLayoutManager(view?.context)
        adapter = CommentAdapter(commentList, requireContext(),this)
        binding.recyclerViewComment.setHasFixedSize(true)
        binding.recyclerViewComment.layoutManager = linearLayoutManager
        binding.recyclerViewComment.adapter = adapter
    }

    override fun onAppEvent(event: AppEvent<String, Objects>) {

    }

    companion object {
        const val VIDEO_ID_KEY = "VIDEO_ID_KEY"

        @JvmStatic
        fun newInstance(videoId : String) =
            CommentFragment().apply {
                arguments = bundleOf(VIDEO_ID_KEY to videoId)
            }
    }

    override fun onItemClick(comment: Comment) {
        replaceFragment(R.id.fragmentContainerVideoDetail, ReplyFragment.newInstance(comment), false, null)
//        pushFragment(ReplyFragment.newInstance(comment),Utils.getSlideTransitionAnimationOptions())
    }
}