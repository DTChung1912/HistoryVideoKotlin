package com.example.historyvideokotlin.fragments

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.historyvideokotlin.R
import com.example.historyvideokotlin.adapters.PostAdapter
import com.example.historyvideokotlin.base.AppEvent
import com.example.historyvideokotlin.base.BaseFragment
import com.example.historyvideokotlin.databinding.FragmentPostBinding
import com.example.historyvideokotlin.viewmodels.PostViewModel
import com.google.android.material.tabs.TabLayoutMediator
import java.util.*


class PostFragment : BaseFragment<PostViewModel, FragmentPostBinding>() {

    private lateinit var postAdapter: PostAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    companion object {

        const val REQUEST_KEY_SELECT_TAB = "REQUEST_KEY_SELECT_TAB"
        const val ARG_TAB_NAME = "ARG_TAB_NAME"

        @JvmStatic
        fun newInstance() =
            PostFragment()
    }

    override fun getLayoutId(): Int = R.layout.fragment_post

    override fun getViewModel(): PostViewModel =
        ViewModelProvider(requireActivity()).get(PostViewModel::class.java)

    override fun getAnalyticsScreenName(): String? = null

    override fun initData() {
        postAdapter = PostAdapter()
        val titleIds = viewModel.getTabTitleIds()
        binding.run {
            viewPager.adapter = postAdapter

        }
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = getString(titleIds[position])
        }.attach()
    }

    override fun onAppEvent(event: AppEvent<String, Objects>) {
    }
}