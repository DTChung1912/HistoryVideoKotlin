package com.example.historyvideokotlin.fragments

import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.historyvideokotlin.R
import com.example.historyvideokotlin.base.AppEvent
import com.example.historyvideokotlin.base.BaseFragment
import com.example.historyvideokotlin.databinding.FragmentPostDetailBinding
import com.example.historyvideokotlin.model.PostListData
import com.example.historyvideokotlin.viewmodels.PostDetailViewModel
import java.util.*

class PostDetailFragment : BaseFragment<PostDetailViewModel,FragmentPostDetailBinding>() {

    private lateinit var postListData : PostListData

    companion object {

        const val POST_DATA_KEY = "POST_DATA_KEY"

        @JvmStatic
        fun newInstance(postListData: PostListData) =
            PostDetailFragment().apply {
                arguments = bundleOf(POST_DATA_KEY to postListData)
            }

    }

    override fun getLayoutId(): Int = R.layout.fragment_post_detail

    override fun getViewModel(): PostDetailViewModel =
        ViewModelProvider(requireActivity()).get(PostDetailViewModel::class.java)

    override fun getAnalyticsScreenName(): String? = null

    override fun initData() {
        postListData = arguments?.getSerializable(POST_DATA_KEY) as PostListData
        binding.run {
            tvTitle.text = postListData.title
            tvYear.text = postListData.year
            tvPostContent.text = postListData.content
            if (postListData.image != null && !postListData.image.isEmpty()) {
                Glide.with(requireContext()).load(postListData.image).into(ivPost)
            }
        }
    }

    override fun onAppEvent(event: AppEvent<String, Objects>) {
    }
}