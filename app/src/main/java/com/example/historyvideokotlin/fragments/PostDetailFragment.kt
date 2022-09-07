package com.example.historyvideokotlin.fragments

import android.widget.RatingBar.OnRatingBarChangeListener
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.historyvideokotlin.R
import com.example.historyvideokotlin.base.AppEvent
import com.example.historyvideokotlin.base.BaseFragment
import com.example.historyvideokotlin.databinding.FragmentPostDetailBinding
import com.example.historyvideokotlin.model.Post
import com.example.historyvideokotlin.utils.HistoryUtils
import com.example.historyvideokotlin.viewmodels.PostDetailViewModel
import java.util.*

class PostDetailFragment : BaseFragment<PostDetailViewModel, FragmentPostDetailBinding>() {

    override fun onResume() {
        super.onResume()
        showBottomMenu(false)
    }

    override fun getLayoutId(): Int = R.layout.fragment_post_detail

    override fun getViewModel(): PostDetailViewModel =
        ViewModelProvider(requireActivity()).get(PostDetailViewModel::class.java)

    override fun getAnalyticsScreenName(): String? = null

    override fun initData() {
        val post = arguments?.getSerializable(POST_DATA_KEY) as Post
//        viewModel.updateReadCountPost(post.post_id, 1)
        binding.run {
            tvTitleToolBar.text = post.title
            tvTitle.text = post.title
            tvTimeline.text = "(" + post.timeline + ")"
            tvPostContent.text = post.content
            if (post.image != null && !post.image.isEmpty()) {
                Glide.with(requireContext()).load(post.image).into(ivPost)
            }

            ratingBar.setOnRatingBarChangeListener(OnRatingBarChangeListener { ratingBar, rating, fromUser ->
                Toast.makeText(
                    requireContext(),
                    rating.toString(),
                    Toast.LENGTH_SHORT
                ).show()
            })

            ivBack.setOnClickListener {
                popFragment(HistoryUtils.getSlideTransitionAnimationOptions())
            }
        }
    }

    override fun onAppEvent(event: AppEvent<String, Objects>) {
    }
    
    companion object {

        const val POST_DATA_KEY = "POST_DATA_KEY"

        @JvmStatic
        fun newInstance(post: Post) =
            PostDetailFragment().apply {
                arguments = bundleOf(POST_DATA_KEY to post)
            }
    }
}