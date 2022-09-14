package com.example.historyvideokotlin.fragments

import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.core.os.bundleOf
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.historyvideokotlin.R
import com.example.historyvideokotlin.repository.HistoryUserManager
import com.example.historyvideokotlin.adapters.CommentAdapter
import com.example.historyvideokotlin.base.AppEvent
import com.example.historyvideokotlin.base.BaseFragment
import com.example.historyvideokotlin.databinding.FragmentCommentBinding
import com.example.historyvideokotlin.dialogfragments.LoginDialogFragment
import com.example.historyvideokotlin.dialogfragments.OnLoginItemClickListener
import com.example.historyvideokotlin.model.Comment
import com.example.historyvideokotlin.utils.HistoryUtils
import com.example.historyvideokotlin.viewmodels.CommentViewModel
import java.util.*

class CommentFragment : BaseFragment<CommentViewModel, FragmentCommentBinding>(),
    CommentAdapter.OnItemClickListener {

    var adapter: CommentAdapter? = null
//    var videoId = "0"

    override fun getLayoutId(): Int {
        return R.layout.fragment_comment
    }

    override fun getViewModel(): CommentViewModel =
        ViewModelProvider(requireActivity()).get(CommentViewModel::class.java)

    override fun getAnalyticsScreenName(): String? = null

    override fun initData() {
        val videoId = arguments?.getInt(VIDEO_ID_KEY)!!
        viewModel.getCommentData(videoId)
        viewModel.commentList.observe(this, { data ->
            data.let {
                setRecyclerView(it)
            }
        })


        binding.run {
            var content = ""
            edtComment.doAfterTextChanged {
                content = it.toString()
            }

            if (content.isEmpty()) {
                ivSend.visibility = GONE
            } else {
                ivSend.visibility = VISIBLE
            }

            edtComment.setOnClickListener {
                if (!HistoryUserManager.checkUserLogined()) {
                    LoginDialogFragment.newInstance(object : OnLoginItemClickListener{
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
                } else {
                    viewModel.updateCommentCountVideo(videoId)
                    viewModel.postComment(HistoryUserManager.FUid(), videoId, content)
                }
            }

            ivBack.setOnClickListener {

            }
        }
    }

    private fun setRecyclerView(commentList: List<Comment>) {
        val linearLayoutManager = LinearLayoutManager(view?.context)
        adapter = CommentAdapter(commentList, requireContext(), this)
        binding.recyclerViewComment.setHasFixedSize(true)
        binding.recyclerViewComment.layoutManager = linearLayoutManager
        binding.recyclerViewComment.adapter = adapter
    }

    override fun onAppEvent(event: AppEvent<String, Objects>) {

    }

    override fun onResume() {
        super.onResume()
        showBottomMenu(false)
    }

    companion object {
        const val VIDEO_ID_KEY = "VIDEO_ID_KEY"

        @JvmStatic
        fun newInstance(videoId: Int) =
            CommentFragment().apply {
                arguments = bundleOf(VIDEO_ID_KEY to videoId)
            }
    }

    override fun onReply(comment: Comment) {
        replaceFragment(
            R.id.fragmentContainerVideoDetail,
            ReplyFragment.newInstance(comment),
            false,
            null
        )
    }

    override fun onLiked(commentId: Int) {
//        viewModel.updateLikeCountComment(commentId)
    }

    override fun onDisliked(commentId: Int) {
//        viewModel.updateDisikeCountComment(commentId)
    }
}