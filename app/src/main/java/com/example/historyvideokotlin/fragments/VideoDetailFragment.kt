package com.example.historyvideokotlin.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.historyvideokotlin.R
import com.example.historyvideokotlin.base.AppEvent
import com.example.historyvideokotlin.base.BaseFragment
import com.example.historyvideokotlin.databinding.FragmentVideoDetailBinding
import com.example.historyvideokotlin.viewmodels.PostDetailViewModel
import com.example.historyvideokotlin.viewmodels.VideoDetailViewModel
import com.google.android.material.tabs.TabLayoutMediator
import java.util.*

class VideoDetailFragment : BaseFragment<VideoDetailViewModel,FragmentVideoDetailBinding>() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_video_detail, container, false)
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            VideoDetailFragment()
    }

    override fun getLayoutId(): Int = R.layout.fragment_video_detail

    override fun getViewModel(): VideoDetailViewModel =
        ViewModelProvider(requireActivity()).get(VideoDetailViewModel::class.java)

    override fun getAnalyticsScreenName(): String? = null

    override fun initData() {
        val titleIds = viewModel.getTabTitleIds()
        binding.run {
            viewPager.adapter = PagerAdapter(this@VideoDetailFragment)
        }
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = getString(titleIds[position])
        }.attach()
    }

    override fun onAppEvent(event: AppEvent<String, Objects>) {
    }

    private class PagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
        override fun getItemCount(): Int = 2

        override fun createFragment(position: Int): Fragment {
            return when (position) {
                0 -> NextVideoFragment.newInstance()
                else -> CommentFragment.newInstance()
            }
        }
    }
}