package com.example.historyvideokotlin.fragments

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.historyvideokotlin.R
import com.example.historyvideokotlin.activities.MainActivity
import com.example.historyvideokotlin.base.BaseFragment
import com.example.historyvideokotlin.databinding.FragmentPostBinding
import com.example.historyvideokotlin.model.Post
import com.example.historyvideokotlin.model.PostType
import com.example.historyvideokotlin.viewmodels.PostViewModel
import com.google.android.material.tabs.TabLayoutMediator

class PostFragment :
    BaseFragment<PostViewModel, FragmentPostBinding>(),
    SwipeRefreshLayout.OnRefreshListener {

    override fun getLayoutId(): Int = R.layout.fragment_post

    override fun getViewModel(): PostViewModel =
        ViewModelProvider(requireActivity()).get(PostViewModel::class.java)

    override fun initData() {
        binding.viewModel = viewModel

        viewModel.getPostData()

        val titleIds = viewModel.getTabTitleIds()
        viewModel.postList.observe(this) {
            binding.viewPager.adapter = PagerAdapter(this@PostFragment, it)
            TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
                tab.text = getString(titleIds[position])
            }.attach()
        }

        binding.toolbar.run {
            tvTitle.text = "Trang chủ"
            ivSearch.setOnClickListener {
                (activity as? MainActivity)?.onSearchClick(0)
            }
            ivMenu.setOnClickListener {
                (activity as? MainActivity)?.onSettingClick()
            }
        }

        binding.tvRefresh.setOnClickListener {
            viewModel.getPostData()
        }

        binding.refreshLayout.setOnRefreshListener(this@PostFragment)
    }

    override fun onResume() {
        super.onResume()
        showBottomMenu()
    }

    private class PagerAdapter(fragment: Fragment, val postList: List<Post>) :
        FragmentStateAdapter(fragment) {

        override fun getItemCount(): Int = 4

        override fun createFragment(position: Int): Fragment {
            return when (position) {
                PostType.PERSON.ordinal -> PostListFragment.newInstance(
                    PostType.PERSON.ordinal,
                    postList
                )
                PostType.EVENT.ordinal -> PostListFragment.newInstance(
                    PostType.EVENT.ordinal,
                    postList
                )
                PostType.PLACE.ordinal -> PostListFragment.newInstance(
                    PostType.PLACE.ordinal,
                    postList
                )
                else -> PostListFragment.newInstance(PostType.TIMELINE.ordinal, postList)
            }
        }
    }

    override fun onRefresh() {
        binding.refreshLayout.isRefreshing = false
        viewModel.getPostData()
    }

    companion object {
        fun newInstance() =
            PostFragment()
    }
}
