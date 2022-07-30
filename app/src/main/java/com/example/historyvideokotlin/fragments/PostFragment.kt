package com.example.historyvideokotlin.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.historyvideokotlin.R
import com.example.historyvideokotlin.base.AppEvent
import com.example.historyvideokotlin.base.BaseFragment
import com.example.historyvideokotlin.databinding.FragmentPostBinding
import com.example.historyvideokotlin.viewmodels.PostViewModel
import com.google.android.material.tabs.TabLayoutMediator
import java.util.*


class PostFragment : BaseFragment<PostViewModel, FragmentPostBinding>() {

    override fun getLayoutId(): Int = R.layout.fragment_post

    override fun getViewModel(): PostViewModel =
        ViewModelProvider(requireActivity()).get(PostViewModel::class.java)

    override fun getAnalyticsScreenName(): String? = null

    override fun initData() {
        viewModel.getPostData()
        val titleIds = viewModel.getTabTitleIds()
        binding.run {
            viewPager.adapter = PagerAdapter(this@PostFragment)

        }
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = getString(titleIds[position])
        }.attach()
    }

    override fun onAppEvent(event: AppEvent<String, Objects>) {
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            PostFragment()
    }

    private class PagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
        override fun getItemCount(): Int = 4

        override fun createFragment(position: Int): Fragment {
            return when (position) {
                Post_Tab.POST_PERON.id -> PostListFragment.newInstance(Post_Tab.POST_PERON.id)
                Post_Tab.POST_EVENT.id -> PostListFragment.newInstance(Post_Tab.POST_EVENT.id)
                Post_Tab.POST_PLACE.id -> PostListFragment.newInstance(Post_Tab.POST_PLACE.id)
                else -> PostListFragment.newInstance(Post_Tab.POST_TIMELINE.id)
            }
        }

        enum class Post_Tab(val id: Int){
            POST_PERON (0),
            POST_EVENT(1),
            POST_PLACE(2),
            POST_TIMELINE(3)
        }
    }
}