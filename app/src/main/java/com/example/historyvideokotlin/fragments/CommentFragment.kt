package com.example.historyvideokotlin.fragments

import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import com.example.historyvideokotlin.R
import com.example.historyvideokotlin.adapters.CommentAdapter
import com.example.historyvideokotlin.base.BaseFragment
import com.example.historyvideokotlin.databinding.FragmentCommentBinding
import com.example.historyvideokotlin.dialogfragments.*
import com.example.historyvideokotlin.model.Comment
import com.example.historyvideokotlin.model.CommentRequest
import com.example.historyvideokotlin.model.Video
import com.example.historyvideokotlin.repository.HistoryUserManager
import com.example.historyvideokotlin.utils.HistoryUtils
import com.example.historyvideokotlin.utils.MyLog
import com.example.historyvideokotlin.viewmodels.CommentViewModel

class CommentFragment :
    BaseFragment<CommentViewModel, FragmentCommentBinding>(),
    CommentAdapter.ItemListener {

    var adapter: CommentAdapter? = null

    //    var videoId = "0"
    private val userId = HistoryUserManager.instance.UserId()
    private lateinit var video: Video
    private val isLikeList = mutableListOf<Int>()
    private val commentList = mutableListOf<Comment>()

    override fun getLayoutId(): Int {
        return R.layout.fragment_comment
    }

    override fun getViewModel(): CommentViewModel =
        ViewModelProvider(requireActivity()).get(CommentViewModel::class.java)

    override fun initData() {
        binding.viewModel = viewModel
        video = arguments?.getSerializable(VIDEO_KEY) as Video

        setAdapter()
        viewModel.isPostComment.observe(viewLifecycleOwner) { isUpdate ->
            isUpdate.let {
                if (isUpdate) {
                    setAdapter()
                }
            }
        }

        binding.edtComment.setOnClickListener {
            if (isLogged()) {
                PostCommentBottomSheetDialogFragment.newInstance(
                    user?.photoUrl.toString(),
                    object :
                        PostCommentListener {
                        override fun onPostComment(content: String) {
                            val comment = CommentRequest(
                                comment_id = null,
                                video_id = video.video_id,
                                user_id = userId,
                                content,
                                HistoryUtils.getCurrentDateAndTime(),
                                0,
                                0,
                                0
                            )
                            viewModel.createComment(comment, video.video_id)
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
        viewModel.getCommentData(video.video_id)
        viewModel.commentList.observe(viewLifecycleOwner) { data ->
            data.let {
                commentList.clear()
                commentList.addAll(it)
                viewModel.getMyCommentList(video.video_id)
            }
        }
        viewModel.isLikeList.observe(viewLifecycleOwner) {
            isLikeList.clear()
            isLikeList.addAll(it)
            updateList()
        }
    }

    private fun updateList() {
        MyLog.e("commentList",commentList.toString())
        MyLog.e("isLikeList",isLikeList.toString())

        adapter = CommentAdapter(commentList, isLikeList, requireContext(), this)
        binding.recyclerViewComment.adapter = adapter
        adapter!!.notifyDataSetChanged()
    }

    override fun onResume() {
        super.onResume()
        hideBottomMenu()
    }

    override fun onReply(comment: Comment, isLike: Int) {
        showUpFragment(
            R.id.fragmentContainerVideoDetail,
            ReplyFragment.newInstance(comment, video, isLike),
            true,
            null
        )
    }

    override fun onLiked(commentId: Int) {
        viewModel.updateCommentLike(commentId)
        updateMyCommentLike(commentId, 1)
    }

    override fun onLikeCancel(commentId: Int) {
        viewModel.updateCommentLikeCancel(commentId)
        updateMyCommentLike(commentId, 0)
    }

    override fun onDisliked(commentId: Int) {
        viewModel.updateCommentDislike(commentId)
        updateMyCommentLike(commentId, 2)
    }

    override fun onDislikeCancel(commentId: Int) {
        viewModel.updateCommentDislikeCancel(commentId)
        updateMyCommentLike(commentId, 0)
    }

    private fun updateMyCommentLike(commentId: Int, isLike: Int) {
        viewModel.updateMyCommentLike(video.video_id, commentId, isLike)
    }

    companion object {
        const val VIDEO_KEY = "VIDEO_KEY"

        fun newInstance(video: Video) =
            CommentFragment().apply {
                arguments = bundleOf(VIDEO_KEY to video)
            }
    }
}
