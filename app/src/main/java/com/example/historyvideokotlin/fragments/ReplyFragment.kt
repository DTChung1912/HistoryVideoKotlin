package com.example.historyvideokotlin.fragments

import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import com.example.historyvideokotlin.R
import com.example.historyvideokotlin.adapters.ReplyAdapter
import com.example.historyvideokotlin.base.BaseFragment
import com.example.historyvideokotlin.databinding.FragmentReplyBinding
import com.example.historyvideokotlin.dialogfragments.*
import com.example.historyvideokotlin.model.*
import com.example.historyvideokotlin.repository.HistoryUserManager
import com.example.historyvideokotlin.utils.HistoryUtils
import com.example.historyvideokotlin.utils.MyLog
import com.example.historyvideokotlin.viewmodels.ReplyViewModel

class ReplyFragment :
    BaseFragment<ReplyViewModel, FragmentReplyBinding>(),
    ReplyAdapter.ItemListener,
    ReplyBottomSheetDialogFragment.OnItemClickListener {

    private val userId = HistoryUserManager.instance.UserId()
    private var adapter: ReplyAdapter? = null
    private lateinit var comment: Comment
    private var replyList = mutableListOf<Reply>()
    private lateinit var video: Video
    private var isCommentLike = 0

    private val isLikeList = mutableListOf<Int>()

    override fun getLayoutId(): Int {
        return R.layout.fragment_reply
    }

    override fun getViewModel(): ReplyViewModel =
        ViewModelProvider(requireActivity()).get(ReplyViewModel::class.java)

    override fun initData() {
        binding.viewModel = viewModel
        comment = arguments?.getSerializable(COMMENT_KEY) as Comment
        video = arguments?.getSerializable(VIDEO_KEY) as Video
        isCommentLike = arguments?.getInt(IS_LIKE_KEY)!!

        setAdapter()
        viewModel.isPostReply.observe(viewLifecycleOwner) { isUpdate ->
            isUpdate.let {
                if (isUpdate) {
                    setAdapter()
                }
            }
        }

        binding.ivLike.setOnClickListener {
            updateLikeComment(1)
        }

        binding.ivDislike.setOnClickListener {
            updateLikeComment(2)
        }

        binding.ivLiked.setOnClickListener {
            updateLikeComment(0)
        }

        binding.ivDisliked.setOnClickListener {
            updateLikeComment(0)
        }

        binding.edtReply.setOnClickListener {
            if (isLogged()) {
                PostCommentBottomSheetDialogFragment.newInstance(
                    user?.photoUrl.toString(),
                    object :
                        PostCommentListener {
                        override fun onPostComment(content: String) {
                            val reply = ReplyRequest(
                                reply_id = null,
                                comment_id = comment.comment_id,
                                user_id = userId,
                                content = content,
                                date_submitted = HistoryUtils.getCurrentDateAndTime(),
                                like_count = 0,
                                dislike_count = 0
                            )
                            viewModel.createReply(reply)
                        }
                    }
                )
                    .show(parentFragmentManager, null)
            } else {
                LoginDialogFragment.newInstance(object : OnLoginItemClickListener {
                    override fun onLogin() {
                        pushFragment(
                            HistoryLoginFragment.newInstance(),
                            HistoryUtils.getSlideTransitionAnimationOptions()
                        )
                    }

                    override fun onRegister() {
                        pushFragment(
                            RegisterFragment.newInstance(),
                            HistoryUtils.getSlideTransitionAnimationOptions()
                        )
                    }
                })
                    .show(parentFragmentManager, null)
            }
        }

        binding.ivSend.setOnClickListener {
            showToast("Bạn chưa nhập bình luận")
        }
    }

    private fun setAdapter() {
        viewModel.getReplyData(comment.comment_id)
        viewModel.setLikeData(isCommentLike)
        viewModel.setComment(comment)

        viewModel.replyList.observe(viewLifecycleOwner) { data ->
            data.let {
                replyList.clear()
                replyList.addAll(it)
                viewModel.getMyReplytList(video.video_id, comment.comment_id)
            }
        }

        viewModel.isLikeList.observe(this, { data ->
            data.let {
                isLikeList.clear()
                isLikeList.addAll(it)
                updateList()
            }
        })
    }

    private fun updateList() {
        MyLog.e("ReplyAdapter",replyList.toString() + " Va " + isLikeList.toString())
        adapter = ReplyAdapter(replyList, isLikeList, requireContext(), this)
        binding.recylerViewReply.adapter = adapter
        adapter!!.notifyDataSetChanged()
    }

    private fun updateLikeComment(isLike: Int) {
        viewModel.updateLikeData(isLike, comment.comment_id)
        viewModel.updateMyCommentLike(video.video_id, comment.comment_id, isLike)
    }

    fun backToVideo() {
        backDownFragment(this)
    }

    override fun onReply(userName: String) {
        ReplyBottomSheetDialogFragment.newInstance(userName, this).show(parentFragmentManager, null)
    }

    override fun onReplyLike(replyId: Int) {
        viewModel.updateReplyLike(replyId)
        updateLikeReply(replyId, 1)
    }

    override fun onReplyDislike(replyId: Int) {
        viewModel.updateReplyDislike(replyId)
        updateLikeReply(replyId, 2)
    }

    override fun onReplyLikeCancel(replyId: Int) {
        viewModel.updateReplyLikeCancel(replyId)
        updateLikeReply(replyId, 0)
    }

    override fun onReplyDislikeCancel(replyId: Int) {
        viewModel.updateReplyDislikeCancel(replyId)
        updateLikeReply(replyId, 0)
    }

    override fun onSend(partnerName: String, content: String) {
//        viewModel.updateReplyCountComment(comment.comment_id)
//        viewModel.createReply(HistoryUserManager.FUid(), partnerName, comment.comment_id, content)
    }

    private fun updateLikeReply(replyId: Int, isLike: Int) {
        viewModel.updateMyReplyLike(video.video_id, comment.comment_id, replyId, isLike)
    }

    companion object {
        const val COMMENT_KEY = "COMMENT_ID_KEY"
        const val VIDEO_KEY = "VIDEO_KEY"
        const val IS_LIKE_KEY = "IS_LIKE_KEY"

        fun newInstance(comment: Comment, video: Video, isCommentLike: Int) =
            ReplyFragment().apply {
                arguments = bundleOf(
                    COMMENT_KEY to comment,
                    VIDEO_KEY to video,
                    IS_LIKE_KEY to isCommentLike
                )
            }
    }
}
