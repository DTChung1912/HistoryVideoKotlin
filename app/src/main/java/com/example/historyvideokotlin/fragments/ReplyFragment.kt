package com.example.historyvideokotlin.fragments

import android.view.View
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.historyvideokotlin.R
import com.example.historyvideokotlin.adapters.ReplyAdapter
import com.example.historyvideokotlin.base.AppEvent
import com.example.historyvideokotlin.base.BaseFragment
import com.example.historyvideokotlin.databinding.FragmentReplyBinding
import com.example.historyvideokotlin.model.Comment
import com.example.historyvideokotlin.model.Reply
import com.example.historyvideokotlin.viewmodels.ReplyViewModel
import java.util.*

class ReplyFragment : BaseFragment<ReplyViewModel, FragmentReplyBinding>(), View.OnClickListener,
    ReplyAdapter.OnItemClickListener {

    private var adapter: ReplyAdapter? = null
    private lateinit var comment: Comment

    override fun getLayoutId(): Int {
        return R.layout.fragment_reply
    }

    override fun getViewModel(): ReplyViewModel =
        ViewModelProvider(requireActivity()).get(ReplyViewModel::class.java)

    override fun getAnalyticsScreenName(): String? = null

    override fun initData() {
        comment = arguments?.getSerializable(COMMENT_KEY) as Comment
        viewModel.getReplyData(comment!!.comment_id)
        viewModel.replyList.observe(this, { data ->
            data.let {
                setRecyclerView(it)
            }
        })

        binding.run {
            if (!comment!!.user_image.isNullOrEmpty()) {
                Glide.with(requireContext()).load(comment!!.user_image).into(civUserAvatar)
            }
            tvUserName.text = comment!!.user_name
            tvDateSubmitted.text = comment!!.date_submitted
            tvContent.text = comment!!.content
            tvLikeCount.text = comment!!.like_count
            tvDislikeCount.text = comment!!.dislike_count
            tvReplyCount.text = comment!!.reply_count
        }
    }

    private fun setRecyclerView(replyList: List<Reply>) {
        val linearLayoutManager = LinearLayoutManager(view?.context)
        adapter = ReplyAdapter(replyList, requireContext(), this)
        binding.recylerViewReply.setHasFixedSize(true)
        binding.recylerViewReply.layoutManager = linearLayoutManager
        binding.recylerViewReply.adapter = adapter
    }

    override fun onAppEvent(event: AppEvent<String, Objects>) {
    }

    companion object {
        const val COMMENT_KEY = "COMMENT_ID_KEY"

        @JvmStatic
        fun newInstance(comment: Comment) =
            ReplyFragment().apply {
                arguments = bundleOf(COMMENT_KEY to comment)
            }
    }

    override fun onItemClick() {
    }

    override fun onClick(v: View?) {
        binding.run {
            when (v) {
                ivLike -> {
                    updateCommentLikeCount()
                }
                ivDislike -> {
                    updateCommnentDislikeCount()
                }
            }
        }

    }

    private fun updateCommnentDislikeCount() {

    }

    private fun updateCommentLikeCount() {

    }
}